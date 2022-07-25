package ru.grachev.market.ui_feature_products_list_api

import androidx.fragment.app.Fragment
import ru.grachev.market.core_utils.navigate.BaseNavigationApi

interface ProductsNavigationApi: BaseNavigationApi {
    fun navigateToProductList(fragment: Fragment)
    fun navigateToPDP(fragment: Fragment, guid: String)
    fun navigateToAddProduct(fragment: Fragment)
}