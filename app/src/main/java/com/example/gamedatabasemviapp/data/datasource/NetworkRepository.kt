package com.example.gamedatabasemviapp.data.datasource

import com.example.gamedatabasemviapp.data.model.GameInfoModel
import com.example.gamedatabasemviapp.presentation.user.UserUiState
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    suspend fun searchGames(queryGame: String): Flow<UserUiState<List<GameInfoModel>>>
}