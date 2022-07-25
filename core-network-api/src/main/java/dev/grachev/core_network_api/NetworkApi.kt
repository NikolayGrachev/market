package dev.grachev.core_network_api

interface NetworkApi {
    fun getProductNetworkApi(): ProductsNetworkApi
    fun getProductInListNetworkApi(): ProductsInListNetworkApi
}