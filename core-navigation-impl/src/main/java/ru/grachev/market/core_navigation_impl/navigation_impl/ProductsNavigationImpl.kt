package ru.grachev.market.core_navigation_impl.navigation_impl

import androidx.fragment.app.Fragment
import ru.grachev.market.core_navigation_impl.utils.CommonNavigation
import ru.grachev.market.core_navigation_impl.utils.NavigationManager.navigate
import ru.grachev.market.ui_feature_add_product_impl.presentation.view.AddProductFragment
import ru.grachev.market.ui_feature_pdp_impl.presentation.view.PDPFragment
import ru.grachev.market.ui_feature_products_list_api.ProductsNavigationApi
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.ProductsFragment
import javax.inject.Inject

class ProductsNavigationImpl @Inject constructor() : CommonNavigation(), ProductsNavigationApi {
    override fun navigateToProductList(fragment: Fragment) {
        fragment.navigate(
            toFragment = ProductsFragment::class.java,
            deepLink = dev.grachev.core_resources.R.string.deep_link_fragment_products_list,
            cleanBackStack = true
        )
    }

    override fun navigateToPDP(fragment: Fragment, guid: String) {
        fragment.navigate(
            toFragment = PDPFragment::class.java,
            deepLink = dev.grachev.core_resources.R.string.deep_link_fragment_pdp_fragment_core,
            argument = guid)
    }

    override fun navigateToAddProduct(fragment: Fragment) {
        fragment.navigate(
            toFragment = AddProductFragment::class.java,
            deepLink = dev.grachev.core_resources.R.string.deep_link_fragment_add_product)
    }
}