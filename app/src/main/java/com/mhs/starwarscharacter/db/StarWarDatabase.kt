package com.mhs.starwarscharacter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhs.starwarscharacter.entity.character.CharacterDetailsDB
import com.mhs.starwarscharacter.entity.character.CharacterListDB
import com.mhs.starwarscharacter.entity.planet.PlanetDetailsDB
import com.mhs.starwarscharacter.entity.planet.PlanetListDB
import com.mhs.starwarscharacter.entity.starShip.StarShipDetailsDB
import com.mhs.starwarscharacter.entity.starShip.StarShipListDB

@Database(entities = [CharacterListDB::class, CharacterDetailsDB::class,
    StarShipListDB::class, StarShipDetailsDB::class,
                     PlanetListDB::class, PlanetDetailsDB::class], version = 1)
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