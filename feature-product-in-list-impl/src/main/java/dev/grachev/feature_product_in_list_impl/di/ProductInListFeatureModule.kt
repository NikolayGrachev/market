package dev.grachev.feature_product_in_list_impl.di

import dagger.Module
import dagger.Provides
import dev.grachev.core_network_api.ProductsInListNetworkApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.feature_product_in_list_impl.data.repository_impl.ProductInListRepositoryImpl
import dev.grachev.feature_product_worker_api.WorkerApi
import ru.grachev.market.core_database_api.ProductsInListDatabaseApi
import ru.grachev.market.core_utils.di.annotation.FeatureScope


@Module
class ProductInListFeatureModule {
    @FeatureScope
    @Provides
    fun provideProductsInListApi(
        databaseApi: ProductsInListDatabaseApi,
        networkApi: ProductsInListNetworkApi,
        workerApi: WorkerApi
    ): ProductsInListApi =
        ProductInListRepositoryImpl(databaseApi, networkApi, workerApi)
}