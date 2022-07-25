package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.BaseRvModel

abstract class BaseViewHolder<T : BaseRvModel>(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(model: BaseRvModel) {
        (model as? T)?.let {
            bindModel(it)
        }
    }

    fun payload(payloads: MutableList<Any>) {
        bindPayload(payloads)
    }

    protected abstract fun bindModel(model: T)
    protected abstract fun bindPayload(payloads: MutableList<Any>)
}