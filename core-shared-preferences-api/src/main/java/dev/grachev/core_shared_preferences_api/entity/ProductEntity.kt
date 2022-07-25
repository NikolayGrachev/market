package dev.grachev.core_shared_preferences_api.entity



data class ProductEntity(
    val guid: String,
    val name: String,
    val price: String,
    val description: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val images: String,
    val weight: Double?,
    val count: Int?,
    val availableCount: Int?,
    val additionalParams: String)



