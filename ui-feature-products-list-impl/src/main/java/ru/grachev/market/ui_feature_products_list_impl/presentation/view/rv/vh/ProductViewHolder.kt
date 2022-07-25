package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_product.view.*
import ru.grachev.market.core_utils.presentation.decorator.IndicatorDecorator
import ru.grachev.market.core_utils.presentation.view.AddToCartButton
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.adapter.ImagesAdapter
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.adapter.ProductsInListAdapter
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.ProductListRvModel

class ProductViewHolder(
    override val containerView: View,
    sharedViewPool: RecyclerView.RecycledViewPool,
    var onItemClick: (Int) -> Unit,
    var onButtonClick: (position: Int, state: AddToCartButton.State) -> Unit
) : BaseViewHolder<ProductListRvModel>(containerView) {

    private val adapter = ImagesAdapter()

    init {
        with(containerView) {
            images.adapter = adapter
            images.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(images)

            images.addItemDecoration(IndicatorDecorator(context))
            images.setRecycledViewPool(sharedViewPool)

            adapter.setOnClick {
                onItemClick.invoke(absoluteAdapterPosition)
            }
        }

        itemView.setOnClickListener {
            onItemClick.invoke(absoluteAdapterPosition)
        }
    }

    override fun bindModel(model: ProductListRvModel) {
        val item = model.product

        adapter.submitList(item.images)

        containerView.apply {
            nameTV.text = item.name
            priceTV.text = "${item.price}₽"

            val pdpCounter = item.pdpVisitsCounter
            if (pdpCounter > 0) {
                visitsTV.visibility = View.VISIBLE
                visitsTV.text = item.pdpVisitsCounter.toString()
            } else {
                visitsTV.visibility = View.GONE
            }

            ratingView.setRating(item.rating.toFloat())

            addToCart.state = if (item.isInCart) {
                AddToCartButton.State.InCart
            } else {
                AddToCartButton.State.AddToCart
            }

            addToCart.onClick = { state ->
                onButtonClick.invoke(absoluteAdapterPosition, state)
            }
        }
    }

    override fun bindPayload(payloads: MutableList<Any>) {
        val bundle: Bundle = payloads[0] as Bundle
        val pdpVisitsCounter = bundle[ProductsInListAdapter.ARG_PDP_VISITS]
        val isInCart = bundle[ProductsInListAdapter.ARG_ADD_IN_CART]

        containerView.apply {
            if (pdpVisitsCounter != null && pdpVisitsCounter is Int && pdpVisitsCounter > 0) {
                visitsTV.visibility = View.VISIBLE
                visitsTV.text = pdpVisitsCounter.toString()
            }

            if (isInCart != null && isInCart is Boolean) {
                addToCart.state = if (isInCart) {
                    AddToCartButton.State.InCart
                } else {
                    AddToCartButton.State.AddToCart
                }
            }
        }
    }
}