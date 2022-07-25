package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.TextRvModel

class TextViewHolder(
    override val containerView: View
): BaseViewHolder<TextRvModel>(containerView) {

    override fun bindModel(model: TextRvModel) {
        (containerView as? AppCompatTextView)?.text = model.text
    }

    override fun bindPayload(payloads: MutableList<Any>) {

    }
}