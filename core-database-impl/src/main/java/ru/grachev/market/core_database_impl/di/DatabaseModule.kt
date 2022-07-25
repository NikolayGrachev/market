package ru.grachev.market.core_database_impl.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.grachev.market.core_database_api.ProductsDatabaseApi
import ru.grachev.market.core_database_api.ProductsInListDatabaseApi
import ru.grachev.market.core_database_impl.ProductsDatabase
import ru.grachev.market.core_database_impl.data.ProductsDatabaseImpl
import ru.grachev.market.core_database_impl.data.ProductsInListDatabaseImpl
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): ProductsDatabase = Room.databaseBuilder(
        context,
        ProductsDatabase::class.java, "products-database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideProductsInListDatabase(db: ProductsDatabase): ProductsInListDatabaseApi =
        ProductsInListDatabaseImpl(db)

    @Singleton
    @Provides
    fun provideProductsDatabase(db: ProductsDatabase): ProductsDatabaseApi =
        ProductsDatabaseImpl(db)
}