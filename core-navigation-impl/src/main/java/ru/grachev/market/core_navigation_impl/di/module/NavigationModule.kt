package ru.grachev.market.core_navigation_impl.di.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Module
import dagger.Provides
import ru.grachev.market.core_navigation_impl.di.factory.NavigationFragmentFactory
import ru.grachev.market.ui_feature_add_product_api.AddProductNavigationApi
import ru.grachev.market.core_navigation_impl.navigation_impl.ProductsNavigationImpl
import ru.grachev.market.ui_feature_pdp_api.PDPNavigationApi
import ru.grachev.market.ui_feature_products_list_api.ProductsNavigationApi
import ru.grachev.market.core_navigation_impl.navigation_impl.AddProductNavigationImpl
import ru.grachev.market.core_navigation_impl.navigation_impl.PDPNavigationImpl
import javax.inject.Provider
import javax.inject.Singleton

@Module
class NavigationModule {

    @Singleton
    @Provides
    fun provideProductNavigation(): ProductsNavigationApi = ProductsNavigationImpl()

    @Singleton
    @Provides
    fun providePDPNavigation(): PDPNavigationApi = PDPNavigationImpl()

    @Singleton
    @Provides
    fun provideAddProductNavigation(): AddProductNavigationApi = AddProductNavigationImpl()

    @Singleton
    @Provides
    fun provideFragmentFactory(
        fragmentProviders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
    ): FragmentFactory {
        return NavigationFragmentFactory(fragmentProviders)
    }
}