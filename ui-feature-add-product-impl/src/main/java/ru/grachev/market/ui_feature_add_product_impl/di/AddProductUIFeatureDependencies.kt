package ru.grachev.market.ui_feature_add_product_impl.di

import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import ru.grachev.market.ui_feature_add_product_api.AddProductNavigationApi


interface AddProductUIFeatureDependencies {
    fun productsInListApi(): ProductsInListApi
    fun productsApi(): ProductsApi
    fun navigation(): AddProductNavigationApi
    fun networkState(): NetworkStateApi
}