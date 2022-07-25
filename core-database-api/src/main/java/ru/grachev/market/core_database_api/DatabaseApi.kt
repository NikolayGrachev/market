package ru.grachev.market.core_database_api

interface DatabaseApi {
    fun getProductsDatabaseApi(): ProductsDatabaseApi
    fun getProductsInListDatabaseApi(): ProductsInListDatabaseApi
}