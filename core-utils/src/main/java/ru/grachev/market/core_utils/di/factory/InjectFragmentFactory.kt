package ru.grachev.market.core_utils.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class InjectFragmentFactory @Inject constructor(
    val providers: Map<String, @JvmSuppressWildcards Provider<Fragment>>) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return providers[className]?.get() ?: super.instantiate(classLoader, className)
    }

    companion object {
        operator fun invoke(
            providers: Map<Class<out Fragment>, Provider<Fragment>>
        ) : InjectFragmentFactory {
            return InjectFragmentFactory(
                providers.mapKeys { (fragmentClass, _) -> fragmentClass.name }
            )
        }
    }
}