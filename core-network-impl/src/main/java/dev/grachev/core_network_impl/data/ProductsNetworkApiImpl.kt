package dev.grachev.core_network_impl.data


import dev.grachev.core_network_api.ProductsNetworkApi
import dev.grachev.core_network_api.models.Product
import dev.grachev.core_network_impl.retrofit_api.ProductsRetrofitApi
import java.lang.Exception
import javax.inject.Inject

class ProductsNetworkApiImpl @Inject constructor(
    private val retrofitApi: ProductsRetrofitApi): ProductsNetworkApi {

    override suspend fun getProducts(): List<Product>? {
        return try {
            retrofitApi.getProducts().execute().body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}