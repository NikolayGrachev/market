package ru.grachev.market.ui_feature_add_product_impl.domain.model

data class ProductInListVO(
    val guid: String,
    val images: List<String>?,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val pdpVisitsCounter: Int = 0
)
