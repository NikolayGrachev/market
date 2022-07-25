package ru.grachev.market.core_utils.model

sealed class NetworkState {
    object Connected: NetworkState()
    object Disconnected: NetworkState()
}