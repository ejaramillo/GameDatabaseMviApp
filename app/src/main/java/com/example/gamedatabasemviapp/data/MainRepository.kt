package com.example.gamedatabasemviapp.data

import com.example.gamedatabasemviapp.data.datasource.NetworkRepository
import com.example.gamedatabasemviapp.presentation.user.UserUiState
import kotlinx.coroutines.flow.Flow

internal class MainRepository(private val networkRepository: NetworkRepository) {

    suspend fun searchGames(queryGame: String): Flow<UserUiState> {
        return networkRepository.searchGames(queryGame)
    }
}