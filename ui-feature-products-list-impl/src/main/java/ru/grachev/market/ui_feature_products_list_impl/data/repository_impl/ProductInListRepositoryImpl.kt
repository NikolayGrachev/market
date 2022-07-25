package ru.grachev.market.ui_feature_products_list_impl.data.repository_impl

import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import ru.grachev.market.core_utils.model.NetworkState
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.ui_feature_products_list_impl.domain.repository.ProductInListRepository
import javax.inject.Inject


class ProductInListRepositoryImpl @Inject constructor(
    private val productsInListApi: ProductsInListApi,
    private val productsApi: ProductsApi,
    private val networkState: NetworkStateApi
    ): ProductInListRepository {

    override suspend fun setOrUpdateProductInList(productInList: ProductInListDTO) {
        val product = productsApi.getProductByIdOnce(productInList.guid)

        var count = product?.count ?: 0
        if (!productInList.isInCart) {
            count = 0
        } else if (count == 0) {
            count++
        }

        val updProduct = product?.copy(isInCart = productInList.isInCart, count = count)

        updProduct?.let {
            productsApi.setOrUpdateProduct(it)
        }
        productsInListApi.setOrUpdateProductInList(productInList)
    }

    override fun getProductsInList(): Flow<List<ProductInListDTO>> =
        productsInListApi.getProductsInListFromCache()

    override fun getProductsInListPeriodically(periodMillis: Long): Flow<List<ProductInListDTO>> =
        productsInListApi.getProductsInListPeriodically(periodMillis)

    override fun startProductsInListWorker() {
        productsInListApi.startWorker()
    }

    override fun getNetworkState(): Flow<NetworkState> = networkState.getNetworkState()
}
