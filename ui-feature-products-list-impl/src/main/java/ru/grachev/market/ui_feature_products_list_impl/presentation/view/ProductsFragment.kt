package ru.grachev.market.ui_feature_products_list_impl.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.sync.Mutex
import ru.grachev.market.core_utils.delegate.BatteryMonitor
import ru.grachev.market.core_utils.delegate.BatteryMonitorImpl
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.core_utils.presentation.fragment.BaseFragment
import ru.grachev.market.ui_feature_products_list_api.ProductsNavigationApi
import ru.grachev.market.ui_feature_products_list_impl.R
import ru.grachev.market.ui_feature_products_list_impl.databinding.FragmentProductsBinding
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.ListGenerator
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.adapter.ProductsInListAdapter
import ru.grachev.market.ui_feature_products_list_impl.presentation.view_model.ProductsViewModel
import javax.inject.Inject



class ProductsFragment @Inject constructor(
    private val  vm: ProductsViewModel,
    override val navigation: ProductsNavigationApi
): BaseFragment<FragmentProductsBinding>(FragmentProductsBinding::inflate),
    BatteryMonitor by BatteryMonitorImpl() {

    val l = mutableListOf<String>()

    override val networkState: LiveData<NetworkState> = vm.networkState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val v: ViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

        observeBatteryChanges {

        }

        vm.getProductsInList(viewLifecycleOwner)

        observeProductsInList(binding?.rvProducts)
        observeNavigation()
        addOnAddButtonClickListener()
    }

    private fun addOnAddButtonClickListener() {
        binding?.btnAddProduct?.setOnClickListener {
            navigation.navigateToAddProduct(this)
        }
    }

    private fun observeNavigation() {
        vm.transaction.observe(viewLifecycleOwner) { event ->
            val guid = event.getContentIfNotHandled() ?: return@observe
            navigation.navigateToPDP(this, guid)
        }
    }

    private fun observeProductsInList(rvProducts: RecyclerView?) {
        vm.productLD.observe(viewLifecycleOwner) { products ->
            initOrUpdateAdapter(rvProducts, products)
        }
    }

    private fun initOrUpdateAdapter(rvProducts: RecyclerView?, products: List<ProductInListVO>) {
        if (rvProducts?.adapter == null) {
            setProductsAdapter(rvProducts, products)
        } else {
            (rvProducts.adapter as ProductsInListAdapter)
                .submitList(ListGenerator.generateListPrice100(requireContext(), products))
        }
    }

    private fun setProductsAdapter(rvProducts: RecyclerView?, products: List<ProductInListVO>) {
        rvProducts?.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)

            val productsAdapter = ProductsInListAdapter(
                { product ->
                    vm.onProductInListClick(product)
                },
                { product, state ->
                    vm.onAddToCartClick(product, state)
                }
            )

            productsAdapter.submitList(ListGenerator.generateListPrice100(requireContext(), products))
            productsAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            adapter = productsAdapter
        }
    }
}