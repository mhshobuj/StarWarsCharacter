package com.mhs.starwarscharacter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhs.starwarscharacter.entity.character.CharacterDetailsDB
import com.mhs.starwarscharacter.entity.character.CharacterListDB
import com.mhs.starwarscharacter.entity.planet.PlanetDetailsDB
import com.mhs.starwarscharacter.entity.planet.PlanetListDB
import com.mhs.starwarscharacter.entity.starShip.StarShipDetailsDB
import com.mhs.starwarscharacter.entity.starShip.StarShipListDB

@Dao
interface StarWarDao {

    // Character operations

    // Add a list of characters to the database, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(characterList: List<CharacterListDB>)

    // Retrieve a list of characters from the database
    @Query("SELECT * FROM characters")
    suspend fun getCharacterList(): List<CharacterListDB>

    // Add character details to the database, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacterDetails(characterDetailsDB: CharacterDetailsDB)

    // Retrieve character details from the database based on the URL
    @Query("SELECT * FROM character_details WHERE url = :url")
    suspend fun getCharacterDetails(url: String): CharacterDetailsDB

    // Starship operations

    // Add a list of starships to the database, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStarShip(starShipList: List<StarShipListDB>)

    // Retrieve a list of starships from the database
    @Query("SELECT * FROM star_ship")
    suspend fun getStarWarList(): List<StarShipListDB>

    // Add starship details to the database, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStarShipDetails(starShipDetailsDB: StarShipDetailsDB)

    // Retrieve starship details from the database based on the URL
    @Query("SELECT * FROM starShip_details WHERE url = :url")
    suspend fun getStarShipDetails(url: String): StarShipDetailsDB

    // Planet operations

    // Add a list of planets to the database, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlanets(planetListDB: List<PlanetListDB>)

    // Retrieve a list of planets from the database
    @Query("SELECT * FROM planets")
    suspend fun getPlanets(): List<PlanetListDB>

    // Add planet details to the database, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlanetDetails(planetDetailsDB: PlanetDetailsDB)

    // Retrieve planet details from the database based on the URL
    @Query("SELECT * FROM planet_details WHERE url = :url")
    suspend fun getPlanetDetails(url: String): PlanetDetailsDB
}