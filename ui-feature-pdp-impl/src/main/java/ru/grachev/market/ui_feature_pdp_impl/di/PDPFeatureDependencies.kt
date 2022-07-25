package ru.grachev.market.ui_feature_pdp_impl.di

import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import ru.grachev.market.ui_feature_pdp_api.PDPNavigationApi


interface PDPFeatureDependencies {
    fun productsApi(): ProductsApi
    fun productsInListApi(): ProductsInListApi
    fun pdpNavigation(): PDPNavigationApi
    fun networkState(): NetworkStateApi
}