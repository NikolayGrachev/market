package ru.grachev.market.ui_feature_add_product_impl.domain.model

data class ProductVO(
    val guid: String,
    val name: String,
    val price: String,
    val description: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val images: List<String>?,
    val weight: Double? = null,
    val count: Int? = null,
    val availableCount: Int? = null,
    val additionalParams: Map<String, String>
)