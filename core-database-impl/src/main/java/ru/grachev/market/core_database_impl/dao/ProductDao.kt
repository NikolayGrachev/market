package ru.grachev.market.core_database_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.grachev.market.core_database_api.entity.ProductEntity

@Dao
abstract class ProductDao {
    @Query("DELETE FROM ProductEntity")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(productEntity: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity")
    abstract fun getAllOnce(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity")
    abstract fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE guid=:guid ")
    abstract fun get(guid: String): Flow<ProductEntity?>

    @Query("SELECT * FROM ProductEntity WHERE guid=:guid ")
    abstract fun getOnce(guid: String): ProductEntity?
}