package com.mhs.starwarscharacter.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //List all supported coins price, market cap, volume, and market related data
    //https://api.coingecko.com/api/v3/coins/markets?vs_currency=eur&order=market_cap_desc&per_page=100&page=1&sparkline=true
    /*@GET("coins/markets?sparkline=true")
    suspend fun getCoinsList(@Query("vs_currency") vs_currency: String): Response<ResponseCoinMarket>*/

    //Get details data (name, price, market, ... including exchange tickers) for a coin
    //https://api.coingecko.com/api/v3/coins/bitcoin?sparkline=true
    /*@GET("coins/{id}?sparkline=true")
    suspend fun getDetailsCoin(@Path("id") id: String) : Response<ResponseDetailsCoin>*/
}