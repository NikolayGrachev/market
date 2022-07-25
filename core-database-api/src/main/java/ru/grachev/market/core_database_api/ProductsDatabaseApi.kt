package ru.grachev.market.core_database_api

import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_database_api.entity.ProductEntity

interface ProductsDatabaseApi {
    fun getProducts(): Flow<List<ProductEntity>>
    fun getProductsOnce(): List<ProductEntity>
    fun getProductById(guid: String): Flow<ProductEntity?>
    fun getProductByIdOnce(guid: String): ProductEntity?
    suspend fun addProduct(product: ProductEntity)
    suspend fun addProducts(products: List<ProductEntity>)
}