package dev.grachev.feature_product_impl.domain.repository

import dev.grachev.feature_product_api.ProductsApi

interface ProductRepository {
    fun getProductsApi(): ProductsApi
}