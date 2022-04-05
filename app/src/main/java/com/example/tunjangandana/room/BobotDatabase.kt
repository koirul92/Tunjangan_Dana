package com.example.tunjangandana.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Dana::class], version = 1)
abstract class BobotDatabase(): RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun danaDao() : DanaDao

    companion object{
        private var INSTANCE: BobotDatabase? = null

        fun getInstance(context: Context):BobotDatabase?{
            if (INSTANCE == null){
                synchronized(BobotDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BobotDatabase::class.java,"Dana.db"
                    ).build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}