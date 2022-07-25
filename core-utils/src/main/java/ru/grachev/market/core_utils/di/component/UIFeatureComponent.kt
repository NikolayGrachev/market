package ru.grachev.market.core_utils.di.component

import androidx.fragment.app.FragmentFactory

interface UIFeatureComponent {
    fun resetComponent()
    fun get(): UIFeatureComponent
    val fragmentFactory: FragmentFactory
}