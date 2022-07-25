package dev.grachev.core_shared_preferences_api.entity



data class ProductInListEntity(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val pdpVisitsCounter: Int = 0)