package com.example.gamedatabasemviapp.data.repository

import com.example.gamedatabasemviapp.data.datasource.RemoteDataSource
import com.example.gamedatabasemviapp.domain.GameList
import kotlinx.coroutines.flow.Flow

class NetworkRepository constructor(val remoteDataSource: RemoteDataSource){
    fun searchGames(queryGame: String): Flow<GameList> =
        remoteDataSource.searchGames(queryGame)
}