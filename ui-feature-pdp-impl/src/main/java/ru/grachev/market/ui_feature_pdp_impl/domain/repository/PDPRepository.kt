package ru.grachev.market.ui_feature_pdp_impl.domain.repository

import dev.grachev.feature_product_api.model.ProductDTO
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_utils.model.NetworkState


interface PDPRepository {
    fun getProductById(guid: String): Flow<ProductDTO?>
    suspend fun setOrUpdateProduct(product: ProductDTO)
    fun getNetworkState(): Flow<NetworkState>
}