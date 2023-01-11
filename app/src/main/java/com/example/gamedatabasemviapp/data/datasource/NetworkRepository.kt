package com.example.gamedatabasemviapp.data.datasource

import com.example.gamedatabasemviapp.data.network.RetrofitService
import com.example.gamedatabasemviapp.data.remote.model.GameResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepository {
    fun searchGames(keyApi: String, queryGame: String): Flow<GameResponseModel> =
        flow {
            val response = RetrofitService.apiService.searchGames(keyApi, queryGame)
            emit(response)
        }

}