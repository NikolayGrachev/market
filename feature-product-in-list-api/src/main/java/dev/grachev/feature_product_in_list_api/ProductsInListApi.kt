package dev.grachev.feature_product_in_list_api

import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import kotlinx.coroutines.flow.Flow

interface ProductsInListApi {
    suspend fun getProductInListById(guid: String): ProductInListDTO?
    suspend fun setOrUpdateProductsInList(products: List<ProductInListDTO>)
    suspend fun setOrUpdateProductInList(product: ProductInListDTO)
    fun getProductsInListFromCache(): Flow<List<ProductInListDTO>>
    fun getProductsInListPeriodically(periodMillis: Long): Flow<List<ProductInListDTO>>
    fun startWorker()
}