package com.example.gamedatabasemviapp.data.repository

import com.example.gamedatabasemviapp.data.datasource.RemoteDataSource
import com.example.gamedatabasemviapp.framework.network.RetrofitService
import com.example.gamedatabasemviapp.data.remote.model.GameResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepository constructor(val remoteDataSource: RemoteDataSource) {
    fun searchGames(queryGame: String): Flow<GameResponseModel> =
        remoteDataSource.searchGames(queryGame)
}