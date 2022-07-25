package ru.grachev.market.ui_feature_products_list_impl.domain.repository

import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import ru.grachev.market.core_utils.model.NetworkState
import kotlinx.coroutines.flow.Flow

interface ProductInListRepository {
    suspend fun setOrUpdateProductInList(product: ProductInListDTO)
    fun getProductsInList(): Flow<List<ProductInListDTO>>
    fun getProductsInListPeriodically(periodMillis: Long): Flow<List<ProductInListDTO>>
    fun startProductsInListWorker()
    fun getNetworkState(): Flow<NetworkState>
}