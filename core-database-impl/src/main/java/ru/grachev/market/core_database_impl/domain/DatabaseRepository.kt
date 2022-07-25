package ru.grachev.market.core_database_impl.domain

import ru.grachev.market.core_database_api.DatabaseApi

interface DatabaseRepository {
    fun getDatabaseApi(): DatabaseApi
}