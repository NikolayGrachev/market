package ru.grachev.market.core_database_api.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val guid: String,
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



