package ru.grachev.market.ui_feature_products_list_api

import android.os.Bundle
import androidx.fragment.app.Fragment

interface RestoreStateApi {
    fun setState(fragment: Fragment, bundle: Bundle?)
    fun restoreState(fragment: Fragment): Bundle?
}