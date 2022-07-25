package dev.grachev.core_network_impl.data

import dev.grachev.core_network_api.ProductsInListNetworkApi
import dev.grachev.core_network_api.models.ProductInList
import dev.grachev.core_network_impl.retrofit_api.ProductsRetrofitApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ProductsInListNetworkApiImpl @Inject constructor(
    private val retrofitApi: ProductsRetrofitApi): ProductsInListNetworkApi {

    override suspend fun getProductsInList(): List<ProductInList>? {
        return try {
            retrofitApi.getProductsInList().execute().body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getProductsInList(periodMillis: Long?): Flow<List<ProductInList>?> = flow {
        if (periodMillis != null) {
            delay(periodMillis)
            while (true) {
                emit(getProductsInList())
                delay(periodMillis)
            }
        } else {
            emit(getProductsInList())
        }
    }
}