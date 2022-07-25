package dev.grachev.feature_product_impl.di

import dagger.Module
import dagger.Provides
import dev.grachev.core_network_api.ProductsNetworkApi
import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_impl.data.repository_impl.ProductsRepositoryImpl
import ru.grachev.market.core_database_api.ProductsDatabaseApi
import ru.grachev.market.core_utils.di.annotation.FeatureScope


@Module
class ProductFeatureModule {
    @FeatureScope
    @Provides
    fun provideProductsApi(
        databaseApi: ProductsDatabaseApi,
        networkApi: ProductsNetworkApi
    ): ProductsApi =
        ProductsRepositoryImpl(databaseApi, networkApi)
}