package com.mhs.starwarscharacter.api

import com.mhs.starwarscharacter.response.character.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("people/")
    suspend fun getCharacterList(@Query("page") page: Int): Response<CharacterList>
}