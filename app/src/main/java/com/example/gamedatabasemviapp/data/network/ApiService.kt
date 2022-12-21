package com.example.gamedatabasemviapp.data.network

import com.example.gamedatabasemviapp.data.model.GameResponseModel
import retrofit2.http.Query

interface ApiService {

    suspend fun searchGames(
        @Query("key") key: String,
        @Query("search") querGame:String
    ):GameResponseModel
}