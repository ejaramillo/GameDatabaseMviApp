package com.example.gamedatabasemviapp.data.datasource

import com.example.gamedatabasemviapp.data.remote.model.GameResponseModel
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun searchGames(queryGame: String): Flow<GameResponseModel>
}