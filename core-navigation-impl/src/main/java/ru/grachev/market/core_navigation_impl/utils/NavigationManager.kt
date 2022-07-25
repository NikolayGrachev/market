package ru.grachev.market.core_navigation_impl.utils

import androidx.annotation.StringRes
import androidx.collection.forEach
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import ru.grachev.market.core_navigation_impl.di.Feature
import ru.grachev.market.core_navigation_impl.di.component.NavigationComponent
import ru.grachev.market.core_navigation_impl.di.factory.NavigationFragmentFactory
import ru.grachev.market.core_utils.di.component.UIFeatureComponent
import ru.grachev.market.core_utils.di.factory.InjectFragmentFactory
import ru.grachev.market.core_utils.getApp
import javax.inject.Provider

object NavigationManager {
    fun <T: Fragment> Fragment.navigate(
        toFragment: Class<T>,
        @StringRes deepLink: Int,
        argument: String? = "",
        cleanBackStack: Boolean = false) {
        with(this) {
            val feature = this.initFeature(toFragment.name)
            addFeatureFragmentProvidersToFragmentFactory(feature)

            val link = getString(deepLink) + argument
            val request = link.getNavDeepLinkRequest()

            findNavController().apply {
                if (cleanBackStack) {
                    popBackStack()
                }
                navigate(request)
            }
        }
    }

    fun Fragment.initFeature(toFragmentLabel: String): UIFeatureComponent? {
        val navGraphLabel = getDestinationNavGraphLabel(this, toFragmentLabel) ?:
        throw Exception("Nav graph for fragment with label $toFragmentLabel not found")

        return Feature.values()
            .find { it.navLabel == navGraphLabel }
            ?.initFeature
            ?.invoke(this.getApp())
    }

    fun Fragment.getComponent(): UIFeatureComponent? {
        val navGraphLabel = this.getCurrentNavGraphLabel()

        return Feature.values()
            .find { it.navLabel == navGraphLabel }
            ?.initFeature
            ?.invoke(this.getApp())
    }

    fun String.getNavDeepLinkRequest() = NavDeepLinkRequest.Builder
        .fromUri(this.toUri())
        .build()

    fun getCurrentNavGraphLabels(fragment: Fragment, currentNavGraphLabel: String): List<CharSequence> {
        val labels = mutableListOf<CharSequence>()
        val graphNodes = fragment.findNavController().graph.nodes

        graphNodes.forEach { id, destination ->
            if (destination is NavGraph && destination.label == currentNavGraphLabel) {
                destination.nodes.forEach { key, value ->
                    value.label?.let {
                        labels.add(it)
                    }
                }
            }
        }
        return labels
    }

    fun Fragment.getCurrentNavGraphLabel(): String {
        val currentFragmentLabel = this.javaClass.name

        val graphNodes = this.findNavController().graph.nodes

        graphNodes.forEach { id, destination ->
            if (destination is NavGraph) {
                destination.nodes.forEach { key, value ->
                    if (value.label == currentFragmentLabel) {
                        return destination.label.toString()
                    }
                }
            }
        }
        throw Exception("Error")
    }

    fun shouldRecreateFeatureOnBackPress(fragment: Fragment): Boolean {
        val prevFragmentLabel = fragment.findNavController()
            .previousBackStackEntry?.destination?.label ?: return false

        return getCurrentNavGraphLabels(fragment, fragment.getCurrentNavGraphLabel())
            .find { it == prevFragmentLabel } == null
    }

    fun shouldResetFeatureOnDetach(fragment: Fragment): Boolean {
        val currentDestinationLabel = fragment.findNavController().currentDestination?.label
        val labels = getCurrentNavGraphLabels(fragment, fragment.getCurrentNavGraphLabel())
        return labels.find { it == currentDestinationLabel } == null
    }

    fun Fragment.resetFeatureFragmentProvidersToFragmentFactory(feature: UIFeatureComponent?) {
        val factory = feature?.fragmentFactory
        activity?.supportFragmentManager?.fragmentFactory = factory
            ?: throw Exception("No fragmentFactory")

        val allProviders = if (NavigationComponent.component?.runtimeFragmentFactory != null) {
            (NavigationComponent.component?.runtimeFragmentFactory as NavigationFragmentFactory).providers
        } else {
            mutableMapOf()
        }.toMutableMap()

        val resetProviders = (feature.fragmentFactory as InjectFragmentFactory).providers

        resetProviders.forEach {
            allProviders.remove(it.key)
        }

        val navFactory = NavigationFragmentFactory(allProviders)

        NavigationComponent.component?.runtimeFragmentFactory = navFactory
    }

    private fun getDestinationNavGraphLabel(fromFragment: Fragment, toFragmentLabel: String): String? {
        val graphNodes = fromFragment.findNavController().graph.nodes

        graphNodes.forEach { id, destination ->
            if (destination is NavGraph) {
                destination.nodes.forEach { key, value ->
                    if (value.label == toFragmentLabel) {
                        return destination.label.toString()
                    }
                }
            }
        }
        return null
    }

    private fun Fragment.addFeatureFragmentProvidersToFragmentFactory(feature: UIFeatureComponent?) {
        val factory = feature?.fragmentFactory
        activity?.supportFragmentManager?.fragmentFactory = factory
            ?: throw Exception("No fragmentFactory")

        var allProviders: Map<String, Provider<Fragment>> = mutableMapOf()
        val defaultProviders = (NavigationComponent.component?.startupFactory as NavigationFragmentFactory).providers
        val featureProviders = if (NavigationComponent.component?.runtimeFragmentFactory != null) {
            (NavigationComponent.component?.runtimeFragmentFactory as NavigationFragmentFactory).providers
        } else {
            mutableMapOf()
        }

        val newProviders = (feature.fragmentFactory as InjectFragmentFactory).providers

        allProviders = defaultProviders + featureProviders + newProviders

        val navFactory = NavigationFragmentFactory(allProviders)

        NavigationComponent.component?.runtimeFragmentFactory = navFactory
    }
}
