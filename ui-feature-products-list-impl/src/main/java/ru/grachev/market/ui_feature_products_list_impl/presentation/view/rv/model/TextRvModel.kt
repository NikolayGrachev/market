package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model

import ru.grachev.market.ui_feature_products_list_impl.R

class TextRvModel(
    val text: String
): BaseRvModel() {
    override val viewType: Int = R.layout.view_holder_text
}