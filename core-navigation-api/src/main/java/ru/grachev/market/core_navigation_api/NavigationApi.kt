package ru.grachev.market.core_navigation_api

import ru.grachev.market.ui_feature_add_product_api.AddProductNavigationApi
import ru.grachev.market.ui_feature_pdp_api.PDPNavigationApi
import ru.grachev.market.ui_feature_products_list_api.ProductsNavigationApi

interface NavigationApi {
    fun getProductNavigation(): ProductsNavigationApi
    fun getPDPNavigation(): PDPNavigationApi
    fun getAddProductNavigation(): AddProductNavigationApi
}