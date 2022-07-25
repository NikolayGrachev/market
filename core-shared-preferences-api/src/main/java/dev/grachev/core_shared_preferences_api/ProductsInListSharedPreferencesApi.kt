package dev.grachev.core_shared_preferences_api

import dev.grachev.core_shared_preferences_api.entity.ProductInListEntity

interface ProductsInListSharedPreferencesApi {
    suspend fun getProductsInList(): List<ProductInListEntity>
    suspend fun getProductInListById(guid: String): ProductInListEntity?
    suspend fun addProductInList(productEntity: ProductInListEntity)
    suspend fun addProductsInList(productsEntity: List<ProductInListEntity>)
}