package ru.grachev.market.core_database_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.grachev.market.core_database_impl.dao.ProductDao
import ru.grachev.market.core_database_impl.dao.ProductInListDao
import ru.grachev.market.core_database_api.entity.ProductEntity
import ru.grachev.market.core_database_api.entity.ProductInListEntity

@Database(entities = [ProductInListEntity::class, ProductEntity::class],
    version = ProductsDatabase.VERSION, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {
    companion object {
        const val VERSION = 4
    }

    abstract fun getProductInListDao(): ProductInListDao
    abstract fun getProductDao(): ProductDao
}