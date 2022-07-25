package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model

import ru.grachev.market.ui_feature_products_list_impl.R
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO

class ProductListRvModel(
    val product: ProductInListVO
): BaseRvModel() {
    override val viewType: Int = R.layout.view_holder_product
}