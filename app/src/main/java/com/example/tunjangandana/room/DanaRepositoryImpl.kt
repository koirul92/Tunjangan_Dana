package com.example.tunjangandana.room

class DanaRepositoryImpl (private val danaDao:DanaDao):DanaRepository{
    override fun getAllDana(): List<Dana> {
        return danaDao.getAllDana()
    }
    override suspend fun insertDana(dana: Dana): Long {
        return danaDao.insertDana(dana = dana)
    }
    override suspend fun updateDana(dana: Dana): Int {
        return danaDao.updateDana(dana = dana)
    }
    override suspend fun deleteDana(dana: Dana): Int {
        return danaDao.deleteDana(dana = dana)
    }
}