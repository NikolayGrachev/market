package ru.grachev.market.core_database_impl.data

import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_database_impl.ProductsDatabase
import ru.grachev.market.core_database_api.ProductsInListDatabaseApi
import ru.grachev.market.core_database_api.entity.ProductInListEntity
import javax.inject.Inject

class ProductsInListDatabaseImpl @Inject constructor(
    private val database: ProductsDatabase
) : ProductsInListDatabaseApi {
    override fun getProductsInList(): Flow<List<ProductInListEntity>> {
        return database.getProductInListDao().getAll()
    }

    override suspend fun getProductsInListOnce(): List<ProductInListEntity> =
        database.getProductInListDao().getAllOnce()

    override suspend fun getProductInListById(guid: String): ProductInListEntity? {
        return database.getProductInListDao().get(guid)
    }

    override suspend fun addProductInList(productEntity: ProductInListEntity) {
        database.getProductInListDao().insert(productEntity)
    }

    override suspend fun addProductsInList(productsEntity: List<ProductInListEntity>) {
        database.getProductInListDao().insertAll(productsEntity)
    }
}