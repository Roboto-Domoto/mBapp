package com.example.mbapp_androidapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mbapp_androidapp.data.entities.NutritionalInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionalInfoDao {

    @Insert
    suspend fun insert(info: NutritionalInfoEntity)

    @Update
    suspend fun update(info: NutritionalInfoEntity)

    @Delete
    suspend fun delete(info: NutritionalInfoEntity)

    @Query("SELECT * FROM nutritional_info_table")
    fun getAll(): Flow<List<NutritionalInfoEntity>>

    @Query("SELECT * FROM nutritional_info_table WHERE info_id = :id")
    fun getByID(id: Int): Flow<NutritionalInfoEntity>

}