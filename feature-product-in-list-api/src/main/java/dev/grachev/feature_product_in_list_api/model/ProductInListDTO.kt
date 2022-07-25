package dev.grachev.feature_product_in_list_api.model

data class ProductInListDTO(
    val guid: String,
    val images: List<String>?,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val pdpVisitsCounter: Int = 0
)