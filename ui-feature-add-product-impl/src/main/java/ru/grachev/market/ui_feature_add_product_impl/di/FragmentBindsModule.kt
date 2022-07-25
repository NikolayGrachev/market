package ru.grachev.market.ui_feature_add_product_impl.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.grachev.market.core_utils.di.annotation.FragmentKey
import ru.grachev.market.ui_feature_add_product_impl.presentation.view.AddProductFragment

@Module
interface FragmentBindsModule {
    @Binds
    @IntoMap
    @FragmentKey(AddProductFragment::class)
    fun bindAddProductFragmentToFragmentForMultiBinding(fragment: AddProductFragment): Fragment
}