package com.example.gamedatabasemviapp.data

import com.example.gamedatabasemviapp.data.datasource.NetworkRepository
import com.example.gamedatabasemviapp.data.model.GameInfoModel
import com.example.gamedatabasemviapp.presentation.user.UserUiState
import kotlinx.coroutines.flow.Flow

class MainRepository(private val networkRepository: NetworkRepository) {

    suspend fun searchGames(queryGame: String): Flow<UserUiState<List<GameInfoModel>>> {
        return networkRepository.searchGames(queryGame)
    }
}