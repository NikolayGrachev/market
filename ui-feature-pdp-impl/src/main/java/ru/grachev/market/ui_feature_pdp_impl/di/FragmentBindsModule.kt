package ru.grachev.market.ui_feature_pdp_impl.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.grachev.market.core_utils.di.annotation.FragmentKey
import ru.grachev.market.ui_feature_pdp_impl.presentation.view.PDPFragment

@Module
interface FragmentBindsModule {
    @Binds
    @IntoMap
    @FragmentKey(PDPFragment::class)
    fun bindAddProductFragmentToFragmentForMultiBinding(fragment: PDPFragment): Fragment
}