package dev.grachev.ui_feature_network_state_monitor_api

import ru.grachev.market.core_utils.model.NetworkState
import kotlinx.coroutines.flow.Flow

interface NetworkStateApi {
    fun getNetworkState(): Flow<NetworkState>
}