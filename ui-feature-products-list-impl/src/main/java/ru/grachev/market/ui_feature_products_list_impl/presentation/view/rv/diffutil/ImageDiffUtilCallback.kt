package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.diffutil

import androidx.recyclerview.widget.DiffUtil

class ImageDiffUtilCallback(
    private val oldList: List<String>,
    private val newList: List<String>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]
        return oldProduct == newProduct
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}