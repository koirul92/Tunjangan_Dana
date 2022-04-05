package com.example.tunjangandana.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface UserDao {
    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun login(email: String, password: String):User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Long
}