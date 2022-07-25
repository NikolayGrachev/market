package ru.grachev.market.core_utils

import android.app.Application
import android.content.Context
import android.view.WindowManager
import androidx.fragment.app.Fragment
import java.io.IOException


/**
 * Helper function to get JSON string from file in assets
 */
fun getJsonFromAssets(context: Context, name: String): String? {
        return try {
            context.assets
                .open("$name.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }

/**
 * Get screen frame rate
 */
fun getRefreshRate(context: Context): Float {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    var rate = display.refreshRate
    if (rate < 10.0f) {
        rate =
            60.0f
    }
    return rate
}

fun Fragment.getApp() = this.requireActivity().applicationContext as Application