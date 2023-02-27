package com.example.gamedatabasemviapp.data.datasource

import com.example.gamedatabasemviapp.domain.GameList
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun searchGames(queryGame: String): Flow<GameList>
}