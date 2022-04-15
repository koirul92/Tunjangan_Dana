package com.example.tunjangandana.room

import android.content.Context

class DanaRepositoryImpl (private val context: Context){

    private val myDatabase = BobotDatabase.getInstance(context)
    suspend fun getAllDana(): List<Dana>? {
        return myDatabase?.danaDao()?.getAllDana()
    }
    suspend fun insertDana(dana: Dana): Long? {
        return myDatabase?.danaDao()?.insertDana(dana = dana)
    }
    suspend fun updateDana(dana: Dana): Int? {
        return myDatabase?.danaDao()?.updateDana(dana = dana)
    }
    suspend fun deleteDana(dana: Dana): Int? {
        return myDatabase?.danaDao()?.deleteDana(dana = dana)
    }
}