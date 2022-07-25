package dev.grachev.core_shared_preferences_impl.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.grachev.market.core_database_api.ProductsDatabaseApi
import ru.grachev.market.core_database_api.entity.ProductEntity
import javax.inject.Inject

class ProductsSPImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val gson: Gson
) : ProductsDatabaseApi {
    companion object {
        const val PRODUCTS = "PRODUCTS"
    }

    override fun getProducts(): Flow<List<ProductEntity>> = flow {
        val productsJson = prefs.getString(PRODUCTS, null)
        if (productsJson == null) {
            emit(listOf())

        } else {
            emit(gson.fromJson<List<ProductEntity>>(
                productsJson,
                object : TypeToken<List<ProductEntity>>() { }.type
            ))
        }
    }

    override fun getProductsOnce(): List<ProductEntity> {
        val productsJson = prefs.getString(PRODUCTS, null) ?: return listOf()

        return gson.fromJson<List<ProductEntity>>(
            productsJson,
            object : TypeToken<List<ProductEntity>>() { }.type
        )
    }

    override fun getProductById(guid: String): Flow<ProductEntity?> {
        return flow {
            emit(getProductsOnce().firstOrNull { it.guid == guid })
        }
    }

    override fun getProductByIdOnce(guid: String): ProductEntity? {
        return getProductsOnce().firstOrNull { it.guid == guid }
    }

    override suspend fun addProduct(product: ProductEntity) {
        val list = getProductsOnce().toMutableList()
        val found = list.find { it.guid == product.guid }
        if (found != null) {
            list.remove(found)
            list.add(product)
        } else {
            list.add(product)
        }
        prefs.edit().putString(PRODUCTS, gson.toJson(list)).apply()
    }

    override suspend fun addProducts(updatedList: List<ProductEntity>) {
        val list = getProductsOnce().toMutableList()

        updatedList.forEach { product ->
            val found = list.find { it.guid == product.guid }

            if (found != null) {
                list.remove(found)
                list.add(product)
            } else {
                list.add(product)
            }
        }
        prefs.edit().putString(PRODUCTS, gson.toJson(list)).apply()
    }
}