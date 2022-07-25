package ru.grachev.market.ui_feature_pdp_impl.data.repository_impl


import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_api.model.ProductDTO
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.ui_feature_pdp_impl.domain.repository.PDPRepository
import javax.inject.Inject


class PDPRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val productsInListApi: ProductsInListApi,
    private val networkState: NetworkStateApi
    ) : PDPRepository {
    override fun getProductById(guid: String): Flow<ProductDTO?> {
        return productsApi.getProductById(guid)
    }

    override suspend fun setOrUpdateProduct(product: ProductDTO) {
        val productInList = productsInListApi.getProductInListById(product.guid)
        val updProduct = productInList?.copy(isInCart = product.isInCart)
        updProduct?.let {
            productsInListApi.setOrUpdateProductInList(it)
        }
        productsApi.setOrUpdateProduct(product)
    }

    override fun getNetworkState(): Flow<NetworkState> = networkState.getNetworkState()
}