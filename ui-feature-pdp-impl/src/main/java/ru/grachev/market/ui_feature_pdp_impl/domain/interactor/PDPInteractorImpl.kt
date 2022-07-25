package ru.grachev.market.ui_feature_pdp_impl.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.ui_feature_pdp_impl.domain.mapper.toDTO
import ru.grachev.market.ui_feature_pdp_impl.domain.mapper.toVO
import ru.grachev.market.ui_feature_pdp_impl.domain.model.ProductVO
import ru.grachev.market.ui_feature_pdp_impl.domain.repository.PDPRepository
import javax.inject.Inject

class PDPInteractorImpl @Inject constructor(
    private val productsRepository: PDPRepository
): PDPInteractor {

    override fun getProductById(guid: String): Flow<ProductVO?> {
        return productsRepository.getProductById(guid).map {
            it?.toVO()
        }
    }

    override suspend fun setOrUpdateProduct(product: ProductVO) {
        return productsRepository.setOrUpdateProduct(product.toDTO())
    }

    override fun getNetworkState(): Flow<NetworkState> = productsRepository.getNetworkState()
}