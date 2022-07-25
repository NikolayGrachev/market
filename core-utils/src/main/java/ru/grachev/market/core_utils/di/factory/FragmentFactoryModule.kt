package ru.grachev.market.core_utils.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Module
import dagger.Provides
import ru.grachev.market.core_utils.di.annotation.FeatureScope
import javax.inject.Provider

@Module
class FragmentFactoryModule {
    @FeatureScope
    @Provides
    fun provideFragmentFactory(
        fragmentProviders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
    ): FragmentFactory {
        return InjectFragmentFactory(fragmentProviders)
    }
}