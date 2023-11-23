package com.mhs.starwarscharacter.api

import com.mhs.starwarscharacter.response.character.CharacterDetails
import com.mhs.starwarscharacter.response.character.CharacterList
import com.mhs.starwarscharacter.response.planet.PlanetDetails
import com.mhs.starwarscharacter.response.planet.PlanetList
import com.mhs.starwarscharacter.response.starShip.StarShipDetails
import com.mhs.starwarscharacter.response.starShip.StarShipList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Character API

    // Get a list of characters with pagination support
    @GET("people/")
    suspend fun getCharacterList(@Query("page") page: Int): Response<CharacterList>

    // Get details of a specific character based on their ID
    @GET("people/{id}/")
    suspend fun getCharacterDetails(@Path("id") id: Int): Response<CharacterDetails>

    // Starship API

    // Get a list of starships with pagination support
    @GET("starships/")
    suspend fun getStarShipList(@Query("page") page: Int): Response<StarShipList>

    // Get details of a specific starship based on its ID
    @GET("starships/{id}/")
    suspend fun getStarShipDetails(@Path("id") id: Int): Response<StarShipDetails>

    // Planets API

    // Get a list of planets with pagination support
    @GET("planets/")
    suspend fun getPlanetsList(@Query("page") page: Int): Response<PlanetList>

    // Get details of a specific planet based on its ID
    @GET("planets/{id}/")
    suspend fun getPlanetDetails(@Path("id") id: Int): Response<PlanetDetails>

}