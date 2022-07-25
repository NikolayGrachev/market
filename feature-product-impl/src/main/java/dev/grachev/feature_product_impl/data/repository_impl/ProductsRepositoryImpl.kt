package dev.grachev.feature_product_impl.data.repository_impl

import dev.grachev.core_network_api.ProductsNetworkApi
import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_api.model.ProductDTO
import dev.grachev.feature_product_impl.domain.mapper.toDTO
import dev.grachev.feature_product_impl.domain.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.grachev.market.core_database_api.ProductsDatabaseApi


class ProductsRepositoryImpl(
    private val database: ProductsDatabaseApi,
    private val network: ProductsNetworkApi): ProductsApi {

    override suspend fun getProducts(): List<ProductDTO> {
        val dbProducts = database.getProductsOnce().map { it.toDTO() }.toMutableList()
        val networkProductInListDTOs = network.getProducts()?.map { it.toDTO() } ?: listOf()

        /**
         * Add to DB new network items
         */
        networkProductInListDTOs.forEach { networkProduct ->
            if (dbProducts.find { it.guid ==  networkProduct.guid} == null) {
                dbProducts.add(networkProduct)
            }
        }

        setOrUpdateProducts(dbProducts)
        return dbProducts
    }

    override fun getProductById(guid: String): Flow<ProductDTO?> {
        return database.getProductById(guid).map { it?.toDTO() ?: getProducts().find { it.guid == guid } }
    }

    override suspend fun getProductByIdOnce(guid: String): ProductDTO? {
        return database.getProductByIdOnce(guid)?.toDTO() ?: getProducts().find { it.guid == guid }
    }

    override suspend fun setOrUpdateProducts(products: List<ProductDTO>) {
        database.addProducts(products.map { it.toEntity() })
    }

    override suspend fun setOrUpdateProduct(product: ProductDTO) {
        database.addProduct(product.toEntity())
    }
}