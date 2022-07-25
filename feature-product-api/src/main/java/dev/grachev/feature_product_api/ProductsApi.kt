package dev.grachev.feature_product_api

import dev.grachev.feature_product_api.model.ProductDTO
import kotlinx.coroutines.flow.Flow

interface ProductsApi {
    suspend fun getProducts(): List<ProductDTO>
    suspend fun getProductByIdOnce(guid: String): ProductDTO?
    fun getProductById(guid: String): Flow<ProductDTO?>
    suspend fun setOrUpdateProducts(products: List<ProductDTO>)
    suspend fun setOrUpdateProduct(product: ProductDTO)
}