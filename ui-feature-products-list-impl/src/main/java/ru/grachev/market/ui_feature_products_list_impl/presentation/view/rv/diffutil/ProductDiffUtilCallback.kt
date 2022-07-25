package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.diffutil

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.adapter.ProductsInListAdapter.Companion.ARG_ADD_IN_CART
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.adapter.ProductsInListAdapter.Companion.ARG_PDP_VISITS
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.BaseRvModel
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.ProductListRvModel
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.TextRvModel

class ProductDiffUtilCallback(
    private val oldList: List<BaseRvModel>,
    private val newList: List<BaseRvModel>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]

        return when (oldProduct) {
            is ProductListRvModel -> {
                if (newProduct is ProductListRvModel) {
                    oldProduct.product.guid == newProduct.product.guid
                } else {
                    false
                }
            }
            is TextRvModel -> {
                if (newProduct is TextRvModel) {
                    oldProduct.text == (newProduct as TextRvModel).text
                } else {
                    false
                }
            }
            else -> {
                true
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]

        return when (oldProduct) {
            is ProductListRvModel -> {
                oldProduct.product.guid == (newProduct as ProductListRvModel).product.guid &&
                oldProduct.product.isInCart == (newProduct as ProductListRvModel).product.isInCart &&
                oldProduct.product.pdpVisitsCounter == (newProduct as ProductListRvModel).product.pdpVisitsCounter
            }
            is TextRvModel -> {
                true
            }
            else -> {
                true
            }
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (newItem is ProductListRvModel && oldItem is ProductListRvModel) {
            val diff = Bundle()

            if (oldItem.product.pdpVisitsCounter != newItem.product.pdpVisitsCounter) {
                diff.putInt(ARG_PDP_VISITS, newItem.product.pdpVisitsCounter)
            }

            if (oldItem.product.isInCart != newItem.product.isInCart) {
                diff.putBoolean(ARG_ADD_IN_CART, newItem.product.isInCart)
            }

            diff
        } else {
            super.getChangePayload(oldItemPosition, newItemPosition)
        }


    }
}