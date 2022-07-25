package ru.grachev.market.ui_feature_add_product_impl.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductVO

interface AddProductInteractor {
    suspend fun setOrUpdateProductInList(product: ProductInListVO)
    suspend fun setOrUpdateProduct(product: ProductVO)
    fun getNetworkState(): Flow<NetworkState>
}
