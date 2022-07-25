package ru.grachev.market.ui_feature_products_list_impl.domain.interactor

import ru.grachev.market.core_utils.model.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.grachev.market.ui_feature_products_list_impl.domain.mapper.toDTO
import ru.grachev.market.ui_feature_products_list_impl.domain.mapper.toVO
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_products_list_impl.domain.repository.ProductInListRepository
import javax.inject.Inject


class ProductsInListInteractorImpl @Inject constructor(
    private val productsRepository: ProductInListRepository
): ProductsInListInteractor {

    override suspend fun setOrUpdateProduct(product: ProductInListVO) {
        return productsRepository.setOrUpdateProductInList(product.toDTO())
    }

    override fun getProductsInList(): Flow<List<ProductInListVO>> =
        productsRepository.getProductsInList().map { list ->
            list.map { it.toVO() }
        }

    override fun getProductsInListPeriodically(periodMillis: Long): Flow<List<ProductInListVO>> =
        productsRepository.getProductsInListPeriodically(periodMillis).map { list ->
            list.map { it.toVO() }
        }

    override fun startProductsInListWorker() {
        productsRepository.startProductsInListWorker()
    }

    override fun getNetworkState(): Flow<NetworkState> = productsRepository.getNetworkState()
}
