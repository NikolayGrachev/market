package ru.grachev.market.ui_feature_add_product_impl.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.ui_feature_add_product_impl.domain.mapper.toDTO
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductVO
import ru.grachev.market.ui_feature_add_product_impl.domain.repository.AddProductRepository
import javax.inject.Inject


class AddProductInteractorImpl @Inject constructor(
    private val addProductRepository: AddProductRepository
): AddProductInteractor {
    override suspend fun setOrUpdateProductInList(product: ProductInListVO) {
        addProductRepository.setOrUpdateProductInList(product.toDTO())
    }

    override suspend fun setOrUpdateProduct(product: ProductVO) {
        addProductRepository.setOrUpdateProduct(product.toDTO())
    }

    override fun getNetworkState(): Flow<NetworkState> = addProductRepository.getNetworkState()
}
