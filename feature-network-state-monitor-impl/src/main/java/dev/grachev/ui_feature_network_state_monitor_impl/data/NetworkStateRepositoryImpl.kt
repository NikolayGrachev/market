package dev.grachev.ui_feature_network_state_monitor_impl.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import ru.grachev.market.core_utils.model.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class NetworkStateRepositoryImpl @Inject constructor(private val context: Context): NetworkStateApi {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun getNetworkState(): Flow<NetworkState> = callbackFlow {

        val currentState = if (connectivityManager.activeNetwork != null) {
            NetworkState.Connected
        } else {
            NetworkState.Disconnected
        }

        trySend(currentState)

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkState.Connected)
            }
            override fun onLost(network: Network) {
                trySend(NetworkState.Disconnected)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}