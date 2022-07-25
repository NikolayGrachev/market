package dev.grachev.feature_product_impl.di

import dev.grachev.core_network_api.ProductsNetworkApi
import ru.grachev.market.core_database_api.ProductsDatabaseApi

interface ProductFeatureDependencies {
    fun productsNetworkApi(): ProductsNetworkApi
    fun productsDatabaseApi(): ProductsDatabaseApi
}