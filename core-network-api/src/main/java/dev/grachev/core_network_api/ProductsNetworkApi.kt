package dev.grachev.core_network_api

import dev.grachev.core_network_api.models.Product

interface ProductsNetworkApi {
    suspend fun getProducts(): List<Product>?
}