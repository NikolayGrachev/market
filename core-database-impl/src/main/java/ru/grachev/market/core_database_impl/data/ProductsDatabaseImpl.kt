package ru.grachev.market.core_database_impl.data

import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_database_impl.ProductsDatabase
import ru.grachev.market.core_database_api.ProductsDatabaseApi
import ru.grachev.market.core_database_api.entity.ProductEntity
import javax.inject.Inject

class ProductsDatabaseImpl @Inject constructor(
    private val database: ProductsDatabase
) : ProductsDatabaseApi {
    override fun getProducts(): Flow<List<ProductEntity>> = database.getProductDao().getAll()

    override fun getProductsOnce(): List<ProductEntity> = database.getProductDao().getAllOnce()

    override fun getProductById(guid: String) = database.getProductDao().get(guid)

    override fun getProductByIdOnce(guid: String) = database.getProductDao().getOnce(guid)

    override suspend fun addProduct(product: ProductEntity) {
        database.getProductDao().insert(product)
    }

    override suspend fun addProducts(products: List<ProductEntity>) {
        database.getProductDao().insertAll(products)
    }
}