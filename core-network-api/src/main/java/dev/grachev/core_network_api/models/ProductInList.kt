package dev.grachev.core_network_api.models

data class ProductInList(
    val guid: String,
    val image: List<String>,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val pdpVisitsCounter: Int = 0
)