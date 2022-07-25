package dev.grachev.feature_product_worker.products_worker.di

import dev.grachev.core_network_api.NetworkApi
import ru.grachev.market.core_database_api.DatabaseApi


interface ProductsWorkerDependencies {
    fun networkApi(): NetworkApi
    fun databaseApi(): DatabaseApi
}