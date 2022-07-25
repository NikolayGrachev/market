package ru.grachev.market.ui_feature_add_product_impl.data.repository_impl

import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_api.model.ProductDTO
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.ui_feature_add_product_impl.domain.repository.AddProductRepository
import javax.inject.Inject


class AddProductRepositoryImpl @Inject constructor(
    private val productsInListApi: ProductsInListApi,
    private val productsApi: ProductsApi,
    private val networkState: NetworkStateApi
    ): AddProductRepository {

    override suspend fun setOrUpdateProduct(product: ProductDTO)  =
        productsApi.setOrUpdateProduct(product)

    override suspend fun setOrUpdateProductInList(productInList: ProductInListDTO) =
        productsInListApi.setOrUpdateProductInList(productInList)

    override fun getNetworkState(): Flow<NetworkState> = networkState.getNetworkState()
}
