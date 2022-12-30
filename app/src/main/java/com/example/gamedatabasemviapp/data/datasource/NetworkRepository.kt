package com.example.gamedatabasemviapp.data.datasource

import com.example.gamedatabasemviapp.data.remote.model.GameInfoModel
import com.example.gamedatabasemviapp.presentation.user.UserUiState
import kotlinx.coroutines.flow.Flow

internal interface NetworkRepository {

    suspend fun searchGames(queryGame: String): Flow<UserUiState>
}