package ru.grachev.market.ui_feature_pdp_impl.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.ui_feature_pdp_impl.domain.model.ProductVO

interface PDPInteractor {
    fun getProductById(guid: String): Flow<ProductVO?>
    suspend fun setOrUpdateProduct(product: ProductVO)
    fun getNetworkState(): Flow<NetworkState>
}