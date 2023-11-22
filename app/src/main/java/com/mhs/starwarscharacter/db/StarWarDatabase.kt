package com.mhs.starwarscharacter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhs.starwarscharacter.entity.character.CharacterListDB

@Database(entities = [CharacterListDB::class], version = 1)
abstract class StarWarDatabase: RoomDatabase() {
    abstract fun starWarDao(): StarWarDao
    companion object{
        @Volatile
        private var INSTANCE: StarWarDatabase? = null

        fun getDatabase(context: Context): StarWarDatabase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        StarWarDatabase::class.java,
                        "starWarDB")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}