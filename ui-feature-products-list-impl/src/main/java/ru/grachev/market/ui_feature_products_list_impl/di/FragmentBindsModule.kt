package ru.grachev.market.ui_feature_products_list_impl.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.grachev.market.core_utils.di.annotation.FragmentKey
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.ProductsFragment

@Module
interface FragmentBindsModule {
    @Binds
    @IntoMap
    @FragmentKey(ProductsFragment::class)
    fun bindProductsFragmentToFragmentForMultiBinding(fragment: ProductsFragment): Fragment
}