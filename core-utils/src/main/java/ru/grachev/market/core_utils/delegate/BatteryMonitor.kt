package ru.grachev.market.core_utils.delegate

interface BatteryMonitor {
    fun observeBatteryChanges(batteryChanges: (batteryCharge: Float) -> Unit)
}