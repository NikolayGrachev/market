package ru.grachev.market.ui_feature_add_product_impl.domain.repository

import dev.grachev.feature_product_api.model.ProductDTO
import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_utils.model.NetworkState

interface AddProductRepository {
    suspend fun setOrUpdateProduct(product: ProductDTO)
    suspend fun setOrUpdateProductInList(productInList: ProductInListDTO)
    fun getNetworkState(): Flow<NetworkState>
}