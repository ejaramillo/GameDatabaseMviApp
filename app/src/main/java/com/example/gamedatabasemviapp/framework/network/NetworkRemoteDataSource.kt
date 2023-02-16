package com.example.gamedatabasemviapp.framework.network

import com.example.gamedatabasemviapp.data.datasource.RemoteDataSource
import com.example.gamedatabasemviapp.data.remote.model.GameResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRemoteDataSource constructor(
    val apikey: String
): RemoteDataSource{
    override fun searchGames(queryGame: String): Flow<GameResponseModel> =
        flow {
            val response = RetrofitService.apiService.searchGames(apikey,queryGame)
            emit(response)
        }

}