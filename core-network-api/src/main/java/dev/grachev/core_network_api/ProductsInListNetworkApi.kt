package dev.grachev.core_network_api

import dev.grachev.core_network_api.models.ProductInList
import kotlinx.coroutines.flow.Flow

interface ProductsInListNetworkApi {
    suspend fun getProductsInList(): List<ProductInList>?
    fun getProductsInList(periodMillis: Long? = null): Flow<List<ProductInList>?>
}