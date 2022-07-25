package ru.grachev.market.core_navigation_impl.di.component

import androidx.fragment.app.FragmentFactory
import dagger.Component
import ru.grachev.market.core_navigation_api.NavigationApi
import ru.grachev.market.core_navigation_impl.di.module.FragmentBindsModule
import ru.grachev.market.core_navigation_impl.di.module.NavigationModule
import ru.grachev.market.core_navigation_impl.presentation.InjectingNavHostFragment
import ru.grachev.market.core_navigation_impl.view.StartupFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ NavigationModule::class, FragmentBindsModule::class, ])
abstract class NavigationComponent: NavigationApi {

    @Singleton
    companion object {
        @Volatile
        var component: NavigationComponent? = null
            private set

        fun initAndGet(): NavigationComponent? {
            if (component == null) {
                synchronized(NavigationComponent::class) {
                    component = DaggerNavigationComponent.builder()
                        .build()
                }
            }
            return component
        }

        fun get(): NavigationComponent? {
            return component
        }
    }

    abstract var startupFactory: FragmentFactory?

    var runtimeFragmentFactory: FragmentFactory? = null

    abstract fun inject(fragment: StartupFragment)
    abstract fun inject(fragment: InjectingNavHostFragment)

}