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

    //for character
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(characterList: List<CharacterListDB>)

    @Query("SELECT * FROM characters")
    suspend fun getCharacterList() : List<CharacterListDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacterDetails(characterDetailsDB: CharacterDetailsDB)

    @Query("SELECT * FROM character_details WHERE url = :url")
    suspend fun getCharacterDetails(url: String) : CharacterDetailsDB

    //for starShip
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStarShip(starShipList: List<StarShipListDB>)

    @Query("SELECT * FROM star_ship")
    suspend fun getStarWarList() : List<StarShipListDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStarShipDetails(starShipDetailsDB: StarShipDetailsDB)

    @Query("SELECT * FROM starShip_details WHERE url = :url")
    suspend fun getStarShipDetails(url: String) : StarShipDetailsDB

    //for planets
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlanets(planetListDB: List<PlanetListDB>)

    @Query("SELECT * FROM planets")
    suspend fun getPlanets() : List<PlanetListDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlanetDetails(planetDetailsDB: PlanetDetailsDB)

    @Query("SELECT * FROM planet_details WHERE url = :url")
    suspend fun getPlanetDetails(url: String) : PlanetDetailsDB
}