package com.example.tunjangandana.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow

@Dao
interface DanaDao {
    @Query("SELECT * FROM Dana")
    fun getAllDana():List<Dana>
    @Insert(onConflict = REPLACE)
    fun insertDana(dana: Dana):Long

    @Update
    fun updateDana(dana: Dana):Int

    @Delete
    fun deleteDana(dana: Dana):Int

}