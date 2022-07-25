package ru.grachev.market.ui_feature_pdp_impl.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.core_utils.presentation.decorator.IndicatorDecorator
import ru.grachev.market.core_utils.presentation.fragment.BaseFragment
import ru.grachev.market.ui_feature_pdp_api.PDPNavigationApi
import ru.grachev.market.ui_feature_pdp_impl.databinding.FragmentPdpBinding
import ru.grachev.market.ui_feature_pdp_impl.presentation.rv.ImagesAdapter
import ru.grachev.market.ui_feature_pdp_impl.presentation.view_model.PDPViewModel
import javax.inject.Inject


class PDPFragment @Inject constructor(
    private val vm: PDPViewModel,
    override val navigation: PDPNavigationApi
): BaseFragment<FragmentPdpBinding>(FragmentPdpBinding::inflate) {
    override val networkState: LiveData<NetworkState> = vm.networkState

    private val adapter = ImagesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getString("product_id") ?: throw Exception("ProductId is null")

        binding?.apply {
            vm.getProductById(productId, viewLifecycleOwner)
            vm.productPDP.observe(this@PDPFragment.viewLifecycleOwner) { item ->
                if (item == null) return@observe

                nameTV.text = item.name
                priceTV.text = "${item.price}₽"
                descriptionTV.text = item.description

                ratingView.rating = item.rating.toFloat()
                quantity.quantity = item.count ?: 0

                adapter.submitList(item.images)
            }
            quantity.onQuantityChanged = { quantity ->
                vm.updateProduct(quantity)
            }
        }

        setImagesAdapter()
    }

    private fun setImagesAdapter() {
        with(binding?.rvImages) {
            this?.adapter = adapter
            this?.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            this?.addItemDecoration(IndicatorDecorator(context))
        }

    }
}