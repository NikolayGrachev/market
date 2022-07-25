package ru.grachev.market.core_navigation_impl.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Provider

class NavigationFragmentFactory(
    val providers: Map<String, Provider<Fragment>>) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragment: Fragment? = providers[className]?.get()
        return fragment ?: super.instantiate(classLoader, className)
    }

    companion object {
        operator fun invoke(
            providers: Map<Class<out Fragment>, Provider<Fragment>>
        ) : NavigationFragmentFactory {
            return NavigationFragmentFactory(
                providers.mapKeys { (fragmentClass, _) -> fragmentClass.name }
            )
        }
    }
}