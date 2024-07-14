package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class], version = 1)
abstract class SongDataBase : RoomDatabase(){
    abstract fun songDao() : SongDao

    // 싱글톤
    companion object{
        private var instance : SongDataBase? = null

        @Synchronized
        fun getInstance(context: Context) : SongDataBase?{
            if(instance == null){
                synchronized(SongDataBase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDataBase::class.java,
                        "song-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}