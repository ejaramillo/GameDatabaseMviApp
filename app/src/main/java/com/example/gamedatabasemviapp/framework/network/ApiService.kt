package com.example.gamedatabasemviapp.framework.network

import com.example.gamedatabasemviapp.domain.GameList
import com.example.gamedatabasemviapp.framework.network.model.GameListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun searchGames(
        @Query("key") key: String,
        @Query("search") querGame:String
    ): GameListResponse
}