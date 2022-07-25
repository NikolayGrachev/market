package dev.grachev.core_shared_preferences_api

import dev.grachev.core_shared_preferences_api.entity.ProductEntity

interface ProductsSharedPreferencesApi {
    suspend fun getProducts(): List<ProductEntity>
    suspend fun getProductById(guid: String): ProductEntity?
    suspend fun addProduct(product: ProductEntity)
    suspend fun addProducts(products: List<ProductEntity>)
}