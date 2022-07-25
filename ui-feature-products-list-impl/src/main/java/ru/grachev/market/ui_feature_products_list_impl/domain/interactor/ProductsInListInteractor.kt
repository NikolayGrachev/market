package ru.grachev.market.ui_feature_products_list_impl.domain.interactor

import ru.grachev.market.core_utils.model.NetworkState
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO


interface ProductsInListInteractor {
    suspend fun setOrUpdateProduct(product: ProductInListVO)
    fun getProductsInList(): Flow<List<ProductInListVO>>
    fun getProductsInListPeriodically(periodMillis: Long): Flow<List<ProductInListVO>>
    fun startProductsInListWorker()
    fun getNetworkState(): Flow<NetworkState>
}
