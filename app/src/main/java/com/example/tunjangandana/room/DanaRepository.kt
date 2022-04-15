package com.example.tunjangandana.room

interface DanaRepository {
    fun getAllDana():List<Dana>
    suspend fun insertDana(dana: Dana):Long
    suspend fun updateDana(dana: Dana):Int
    suspend fun deleteDana(dana: Dana):Int
}