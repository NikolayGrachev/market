package ru.grachev.market.core_database_api.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductInListEntity(
    @PrimaryKey val guid: String,
    val images: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val pdpVisitsCounter: Int = 0)