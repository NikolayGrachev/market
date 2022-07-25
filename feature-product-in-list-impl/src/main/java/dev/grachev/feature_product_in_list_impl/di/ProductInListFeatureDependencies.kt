package dev.grachev.feature_product_in_list_impl.di

import dev.grachev.core_network_api.ProductsInListNetworkApi
import dev.grachev.feature_product_worker_api.WorkerApi
import ru.grachev.market.core_database_api.ProductsInListDatabaseApi

interface ProductInListFeatureDependencies {
    fun productsInListNetworkApi(): ProductsInListNetworkApi
    fun productsInListDatabaseApi(): ProductsInListDatabaseApi
    fun workerApi(): WorkerApi
}