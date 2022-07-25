package dev.grachev.core_network_impl.retrofit_api

import dev.grachev.core_network_api.models.Product
import dev.grachev.core_network_api.models.ProductInList
import retrofit2.Call
import retrofit2.http.GET

interface ProductsRetrofitApi {
    @GET("d1b4763b-a5ea-471f-83bf-796da466e3d8")
    fun getProducts(): Call<List<Product>>

    @GET("ee6876a1-8c02-45aa-bde4-b91817a8b210")
    fun getProductsInList(): Call<List<ProductInList>>
}