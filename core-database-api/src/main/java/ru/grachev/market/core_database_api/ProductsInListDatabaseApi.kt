package ru.grachev.market.core_database_api

import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_database_api.entity.ProductInListEntity

interface ProductsInListDatabaseApi {
    fun getProductsInList(): Flow<List<ProductInListEntity>>
    suspend fun getProductsInListOnce(): List<ProductInListEntity>
    suspend fun getProductInListById(guid: String): ProductInListEntity?
    suspend fun addProductInList(productEntity: ProductInListEntity)
    suspend fun addProductsInList(productsEntity: List<ProductInListEntity>)
}