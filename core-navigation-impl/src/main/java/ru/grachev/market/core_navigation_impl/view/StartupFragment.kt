package ru.grachev.market.core_navigation_impl.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.grachev.market.ui_feature_products_list_api.ProductsNavigationApi
import javax.inject.Inject


/**
 * Fragment to start first fragment and use Jetpack Navigation
 */

class StartupFragment @Inject constructor(
    var navigationApi: ProductsNavigationApi): Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationApi.navigateToProductList(this)
    }
}