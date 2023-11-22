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

    /** character api **/
    @GET("people/")
    suspend fun getCharacterList(@Query("page") page: Int): Response<CharacterList>

    @GET("people/{id}/")
    suspend fun getCharacterDetails(@Path("id") id: Int): Response<CharacterDetails>

    /** starship api **/
    @GET("starships/")
    suspend fun getStarShipList(@Query("page") page: Int): Response<StarShipList>

    @GET("starships/{id}/")
    suspend fun getStarShipDetails(@Path("id") id: Int): Response<StarShipDetails>

    /** planets api **/
    @GET("planets/")
    suspend fun getPlanetsList(@Query("page") page: Int): Response<PlanetList>

    @GET("planets/{id}/")
    suspend fun getPlanetDetails(@Path("id") id: Int): Response<PlanetDetails>

}