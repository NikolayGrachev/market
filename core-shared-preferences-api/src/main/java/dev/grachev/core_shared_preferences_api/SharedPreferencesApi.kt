package dev.grachev.core_shared_preferences_api

interface SharedPreferencesApi {
    fun getProductsSharedPreferencesApi(): ProductsSharedPreferencesApi
    fun getProductsInListSharedPreferencesApi(): ProductsInListSharedPreferencesApi
}