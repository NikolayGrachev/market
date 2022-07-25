package ru.grachev.market.core_navigation_impl.presentation

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import ru.grachev.market.core_navigation_impl.di.component.NavigationComponent
import javax.inject.Inject

class InjectingNavHostFragment : NavHostFragment() {
    @Inject
    lateinit var daggerFragmentInjectionFactory: FragmentFactory

    override fun onAttach(context: Context) {
        NavigationComponent.initAndGet()
        NavigationComponent.component?.inject(this)

        /**
         * After orientation changed fragmentFactory would be set to last used value
         */
        val factory = NavigationComponent.component?.runtimeFragmentFactory ?:
            NavigationComponent.component?.startupFactory

        activity?.supportFragmentManager?.fragmentFactory = factory ?: throw Exception()

        super.onAttach(context)
    }
}