package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.grachev.market.core_utils.presentation.view.AddToCartButton
import ru.grachev.market.ui_feature_products_list_impl.R
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh.BaseViewHolder
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh.ProductViewHolder
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh.TextViewHolder

class ViewHolderFactory {
    fun create(
        parentView: ViewGroup,
        viewType: Int,
        nestedRecyclerViewPool: RecyclerView.RecycledViewPool,
        onItemClick: (Int) -> Unit,
        onButtonClick: (position: Int, state: AddToCartButton.State) -> Unit
    ): BaseViewHolder<*> {
        lateinit var vh: BaseViewHolder<*>
        when (viewType) {
            R.layout.view_holder_text -> {
                vh = TextViewHolder(
                    LayoutInflater.from(parentView.context).inflate(viewType, parentView, false)
                )
            }

            R.layout.view_holder_product -> {
                vh = ProductViewHolder(
                    LayoutInflater.from(parentView.context).inflate(viewType, parentView, false),
                    nestedRecyclerViewPool,
                    onItemClick,
                    onButtonClick
                )
            }

            else -> throw Exception("")
        }

        return vh
    }
}