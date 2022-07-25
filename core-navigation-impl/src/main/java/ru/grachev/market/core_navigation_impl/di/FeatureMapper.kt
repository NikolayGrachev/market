package ru.grachev.market.core_navigation_impl.di

import android.app.Application
import ru.grachev.market.core_navigation_impl.di.InitializeFeature.initFeatureAddProduct
import ru.grachev.market.core_navigation_impl.di.InitializeFeature.initFeaturePDP
import ru.grachev.market.core_navigation_impl.di.InitializeFeature.initFeatureProductsList
import ru.grachev.market.core_utils.di.component.UIFeatureComponent
import ru.grachev.market.ui_feature_add_product_impl.di.AddProductUIFeatureComponent
import ru.grachev.market.ui_feature_add_product_impl.di.DaggerAddProductUIFeatureComponent_AddProductUIFeatureDependenciesComponent
import ru.grachev.market.ui_feature_pdp_impl.di.DaggerPDPFeatureComponent_PDPFeatureDependenciesComponent
import ru.grachev.market.ui_feature_pdp_impl.di.PDPFeatureComponent
import ru.grachev.market.ui_feature_products_list_impl.di.DaggerProductsListUIFeatureComponent_ProductsListUIFeatureDependenciesComponent
import ru.grachev.market.ui_feature_products_list_impl.di.ProductsListUIFeatureComponent

enum class Feature(val navLabel: String, val initFeature: Application.() -> UIFeatureComponent?) {
    PRODUCTS_LIST(NavLabel.PRODUCTS_LIST, { initFeatureProductsList() }),
    PDP(NavLabel.PDP, { initFeaturePDP() }),
    ADD_PRODUCT(NavLabel.ADD_PRODUCT, { initFeatureAddProduct() }),
}

private object NavLabel {
    const val PRODUCTS_LIST = "navigation_products"
    const val PDP = "navigation_pdp"
    const val ADD_PRODUCT = "navigation_add_product"
}

private object InitializeFeature {
    fun Application.initFeatureProductsList(): ProductsListUIFeatureComponent? {
        val productsInListApi = CoreFeatureInjector.getProductsInListApi(this)

        return ProductsListUIFeatureComponent.initAndGet(this,
            DaggerProductsListUIFeatureComponent_ProductsListUIFeatureDependenciesComponent.builder()
                .productsInListApi(productsInListApi)
                .productsApi(CoreFeatureInjector.getProductsApi(this))
                .productsNavigationApi(CoreFeatureInjector.getNavigation()?.getProductNavigation())
                .networkStateApi(CoreFeatureInjector.getNetworkStateApi(this))
                .build()
        )
    }

    fun Application.initFeatureAddProduct(): AddProductUIFeatureComponent? {
        val productsInListApi = CoreFeatureInjector.getProductsInListApi(this)
        val productsApi = CoreFeatureInjector.getProductsApi(this)

        return AddProductUIFeatureComponent.initAndGet(
            DaggerAddProductUIFeatureComponent_AddProductUIFeatureDependenciesComponent.builder()
                .productsInListApi(productsInListApi)
                .productsApi(productsApi)
                .addProductNavigationApi(CoreFeatureInjector.getNavigation()?.getAddProductNavigation())
                .networkStateApi(CoreFeatureInjector.getNetworkStateApi(this))
                .build()
        )
    }

    fun Application.initFeaturePDP(): PDPFeatureComponent? {
        val productsApi = CoreFeatureInjector.getProductsApi(this)
        val productsInListApi = CoreFeatureInjector.getProductsInListApi(this)

        return PDPFeatureComponent.initAndGet(
            DaggerPDPFeatureComponent_PDPFeatureDependenciesComponent.builder()
                .productsApi(productsApi)
                .productsInListApi(productsInListApi)
                .pDPNavigationApi(CoreFeatureInjector.getNavigation()?.getPDPNavigation())
                .networkStateApi(CoreFeatureInjector.getNetworkStateApi(this))
                .build()
        )
    }
}