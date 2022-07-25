package ru.grachev.market.core_utils.navigate

import androidx.fragment.app.Fragment
import ru.grachev.market.core_utils.di.component.UIFeatureComponent

interface BaseNavigationApi {
    fun resetComponent(fragment: Fragment)
    fun getComponent(fragment: Fragment): UIFeatureComponent?
    fun navigateBack(fragment: Fragment)
}