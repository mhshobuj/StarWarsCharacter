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
    StarShipListDB::class, StarShipDetailsDB::class, PlanetListDB::class, PlanetDetailsDB::class], version = 1)
abstract class StarWarDatabase: RoomDatabase() {
    // Abstract method to get the Data Access Object (DAO) for database operations
    abstract fun starWarDao(): StarWarDao

    companion object {
        // Volatile variable to ensure that INSTANCE is always up-to-date across all threads
        @Volatile
        private var INSTANCE: StarWarDatabase? = null

        // Method to get an instance of the database. It follows the Singleton pattern.
        fun getDatabase(context: Context): StarWarDatabase {
            // If INSTANCE is null, create a new database instance
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context,
                        StarWarDatabase::class.java,
                        "starWarDB")
                        .build()
                }
            }
            // Return the existing or newly created database instance
            return INSTANCE!!
        }
    }
}