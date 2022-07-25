package dev.grachev.feature_product_in_list_impl.domain.repository

import dev.grachev.feature_product_in_list_api.ProductsInListApi

interface ProductInListRepository {
    fun getProductsInListApi(): ProductsInListApi
}