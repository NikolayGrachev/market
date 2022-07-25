package ru.grachev.market.core_database_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_database_api.entity.ProductInListEntity

@Dao
abstract class ProductInListDao {
    @Query("DELETE FROM ProductInListEntity")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(productEntity: ProductInListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(products: List<ProductInListEntity>)

    @Query("SELECT * FROM ProductInListEntity")
    abstract fun getAll(): Flow<List<ProductInListEntity>>

    @Query("SELECT * FROM ProductInListEntity")
    abstract fun getAllOnce(): List<ProductInListEntity>

    @Query("SELECT * FROM ProductInListEntity WHERE guid=:guid ")
    abstract suspend fun get(guid: String): ProductInListEntity
}