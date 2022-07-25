package dev.grachev.core_shared_preferences_impl.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.grachev.market.core_database_api.ProductsInListDatabaseApi
import ru.grachev.market.core_database_api.entity.ProductInListEntity
import javax.inject.Inject

class ProductsInListSPImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val gson: Gson
) : ProductsInListDatabaseApi {
    companion object {
        const val PRODUCTS_IN_LIST = "PRODUCTS_IN_LIST"
    }

    override fun getProductsInList(): Flow<List<ProductInListEntity>> = flow {
        val productsJson = prefs.getString(PRODUCTS_IN_LIST, null)
        if (productsJson == null) {
            emit(listOf())

        } else {
            emit(gson.fromJson<List<ProductInListEntity>>(
                productsJson,
                object : TypeToken<List<ProductInListEntity>>() { }.type
            ))
        }
    }

    override suspend fun getProductsInListOnce(): List<ProductInListEntity> {
        val productsJson = prefs.getString(PRODUCTS_IN_LIST, null) ?: return listOf()

        return gson.fromJson<List<ProductInListEntity>>(
            productsJson,
            object : TypeToken<List<ProductInListEntity>>() { }.type
        )
    }

    override suspend fun getProductInListById(guid: String): ProductInListEntity? {
        return getProductsInListOnce().firstOrNull { it.guid == guid }
    }

    override suspend fun addProductInList(productEntity: ProductInListEntity) {
        val list = getProductsInListOnce().toMutableList()
        val found = list.find { it.guid == productEntity.guid }
        if (found != null) {
            list.remove(found)
            list.add(productEntity)
        } else {
            list.add(productEntity)
        }
        prefs.edit().putString(PRODUCTS_IN_LIST, gson.toJson(list)).apply()
    }

    override suspend fun addProductsInList(updatedList: List<ProductInListEntity>) {
        val list = getProductsInListOnce().toMutableList()

        updatedList.forEach { product ->
            val found = list.find { it.guid == product.guid }

            if (found != null) {
                list.remove(found)
                list.add(product)
            } else {
                list.add(product)
            }
        }
        prefs.edit().putString(PRODUCTS_IN_LIST, gson.toJson(list)).apply()
    }
}