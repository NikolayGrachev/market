package dev.grachev.ui_feature_network_state_monitor_api

interface NetworkMonitorApi {
    fun getNetworkState(): NetworkStateApi
}