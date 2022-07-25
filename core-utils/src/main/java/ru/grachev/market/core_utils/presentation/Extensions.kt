package ru.grachev.market.core_utils.presentation

import android.content.Context
import android.util.TypedValue

fun <T>T.asEvent() = Event<T>(this)

fun Int.toPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}