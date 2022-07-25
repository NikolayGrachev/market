package dev.grachev.feature_product_in_list_impl.data.repository_impl

import dev.grachev.core_network_api.ProductsInListNetworkApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import dev.grachev.feature_product_in_list_impl.domain.mapper.toDTO
import dev.grachev.feature_product_in_list_impl.domain.mapper.toEntity
import dev.grachev.feature_product_worker_api.WorkerApi
import ru.grachev.market.core_database_api.ProductsInListDatabaseApi
import kotlinx.coroutines.flow.*


class ProductInListRepositoryImpl(
    private val database: ProductsInListDatabaseApi,
    private val network: ProductsInListNetworkApi,
    private val workerApi: WorkerApi
    ): ProductsInListApi {

    override fun startWorker() {
        workerApi.beginWorkToGetProductsInListAndProducts()
    }

    override fun getProductsInListFromCache(): Flow<List<ProductInListDTO>> =
        database.getProductsInList().map { it.map { it.toDTO() } }

    /**
     * Request products from network, compare with DB values and update DB
     */
    override fun getProductsInListPeriodically(periodMillis: Long): Flow<List<ProductInListDTO>> =
        database.getProductsInList().combine(network.getProductsInList(periodMillis)) { db, network ->
            updateProductsInList(db.map { it.toDTO() }, network?.map { it.toDTO() })
        }

    override suspend fun getProductInListById(guid: String): ProductInListDTO? {
        return database.getProductInListById(guid)?.toDTO()
    }

    override suspend fun setOrUpdateProductsInList(products: List<ProductInListDTO>) {
        database.addProductsInList(products.map { it.toEntity() })
    }

    override suspend fun setOrUpdateProductInList(product: ProductInListDTO) {
        database.addProductInList(product.toEntity())
    }

    private suspend fun updateProductsInList(
        dbProducts: List<ProductInListDTO>,
        nwProducts: List<ProductInListDTO>?
    ): List<ProductInListDTO> {
        val updatedDbProducts = mutableListOf<ProductInListDTO>()
        updatedDbProducts.addAll(dbProducts)

        val newProducts = mutableListOf<ProductInListDTO>()

        nwProducts?.forEach { networkProduct ->
            if (dbProducts.find { it.guid ==  networkProduct.guid} == null) {
                newProducts.add(networkProduct)
            }
        }
        setOrUpdateProductsInList(newProducts)

        updatedDbProducts.addAll(newProducts)
        return updatedDbProducts
    }
}