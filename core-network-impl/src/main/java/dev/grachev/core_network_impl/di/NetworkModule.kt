package dev.grachev.core_network_impl.di

import android.content.Context
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.grachev.core_network_api.ProductsInListNetworkApi
import dev.grachev.core_network_api.ProductsNetworkApi
import dev.grachev.core_network_impl.data.ProductsNetworkApiImpl
import dev.grachev.core_network_impl.data.ProductsInListNetworkApiImpl
import dev.grachev.core_network_impl.retrofit_api.ProductsRetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsRetrofitApi(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): ProductsRetrofitApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ProductsRetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofitApi: ProductsRetrofitApi): ProductsNetworkApi =
        ProductsNetworkApiImpl(retrofitApi)

    @Singleton
    @Provides
    fun provideProductInListApi(retrofitApi: ProductsRetrofitApi): ProductsInListNetworkApi =
        ProductsInListNetworkApiImpl(retrofitApi)
}