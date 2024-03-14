package com.example.mbapp_androidapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mbapp_androidapp.data.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert
    suspend fun insert(item: ItemEntity)

    @Update
    suspend fun update(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

    @Query("SELECT * FROM items_table")
    fun getAll(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items_table WHERE itemId = :id")
    fun getByID(id: Int): Flow<ItemEntity>

    @Query("SELECT * FROM items_table WHERE name = :name")
    fun getByName(name: String): Flow<List<ItemEntity>>
}