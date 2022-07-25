package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.grachev.market.core_utils.presentation.view.AddToCartButton
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.ViewHolderFactory
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.ViewTypes
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.diffutil.ProductDiffUtilCallback
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.BaseRvModel
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.ProductListRvModel
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh.BaseViewHolder


class ProductsInListAdapter(
    var onItemClick: (ProductInListVO) -> Unit,
    var onButtonClick: (product: ProductInListVO, state: AddToCartButton.State) -> Unit,
    var items: MutableList<BaseRvModel> = mutableListOf(),
     private val vhFactory: ViewHolderFactory = ViewHolderFactory()
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        const val ARG_PDP_VISITS = "arg_pdp_visits"
        const val ARG_ADD_IN_CART = "arg_add_in_cart"
    }

    private val nestedRecyclerViewPool = RecyclerView.RecycledViewPool().apply {
        this.setMaxRecycledViews(ViewTypes.IMAGE_VIEW_TYPE, 25)
        this.setMaxRecycledViews(ViewTypes.PROPERTY_VIEW_TYPE, 25)
    }

    fun submitList(newList: List<BaseRvModel>) {
        val productDiffUtilCallback = ProductDiffUtilCallback(this.items.toList(), newList)
        val productDiffResult = DiffUtil.calculateDiff(productDiffUtilCallback)

        this.items.clear()
        this.items.addAll(newList)

        productDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return vhFactory.create(
            parent,
            viewType,
            nestedRecyclerViewPool,
            { position ->
                onItemClick.invoke((items[position] as ProductListRvModel).product)
            },
            { position, state ->
                onButtonClick.invoke((items[position] as ProductListRvModel).product, state)
            }
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.payload(payloads)
        } else {
            holder.bind(items[position])
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType
}
