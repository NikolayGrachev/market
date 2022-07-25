package ru.grachev.market.core_navigation_impl.di.module

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.grachev.market.core_navigation_impl.view.StartupFragment
import ru.grachev.market.core_utils.di.annotation.FragmentKey

@Module
interface FragmentBindsModule {
    @Binds
    @IntoMap
    @FragmentKey(StartupFragment::class)
    fun bindStartupFragmentToFragmentForMultiBinding(fragment: StartupFragment): Fragment
}