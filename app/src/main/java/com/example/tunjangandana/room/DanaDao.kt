package com.example.tunjangandana.room

import androidx.room.*

@Dao
interface DanaDao {
    @Query("SELECT * FROM Dana")
    fun getAllDana():List<Dana>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDana(dana: Dana):Long

    @Update
    fun updateDana(dana: Dana):Int

    @Delete
    fun deleteDana(dana: Dana):Int

}