package ru.grachev.market.core_navigation_impl.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.grachev.market.core_navigation_impl.utils.NavigationManager.getComponent
import ru.grachev.market.core_navigation_impl.utils.NavigationManager.initFeature
import ru.grachev.market.core_navigation_impl.utils.NavigationManager.shouldRecreateFeatureOnBackPress
import ru.grachev.market.core_utils.navigate.BaseNavigationApi

abstract class CommonNavigation: BaseNavigationApi {
    override fun getComponent(fragment: Fragment) = fragment.getComponent()

    override fun resetComponent(fragment: Fragment) {
        val shouldResetFeatureOnDetach = NavigationManager.shouldResetFeatureOnDetach(fragment)
        val featureComponent = fragment.getComponent()

        if (shouldResetFeatureOnDetach) {
            ///fragment.resetFeatureFragmentProvidersToFragmentFactory(featureComponent)
            featureComponent?.resetComponent()
        }
    }

    override fun navigateBack(fragment: Fragment) {
        val prevFragmentLabel = fragment.findNavController().previousBackStackEntry?.destination?.label

        if (prevFragmentLabel == null || !shouldRecreateFeatureOnBackPress(fragment)) {
            fragment.requireActivity().onBackPressed()
            return
        }

        fragment.initFeature(prevFragmentLabel.toString())
        fragment.requireActivity().onBackPressed()
    }
}