package com.example.gamedatabasemviapp.data.datasource

import com.example.gamedatabasemviapp.data.model.GameInfoModel
import com.example.gamedatabasemviapp.data.network.ApiService
import com.example.gamedatabasemviapp.presentation.user.UserUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkRepositoryImpl(
    private val apiService: ApiService
) : NetworkRepository {
    override suspend fun searchGames(queryGame: String): Flow<UserUiState<List<GameInfoModel>>> =
        flow {
            try {
                val response = apiService.searchGames("a51ef38e6d754bdc919a4104b0387fa3", queryGame)
                val games = response.gameDataResults
                when {
                    games.isNullOrEmpty() -> emit(UserUiState.Empty)
                    else -> emit(UserUiState.Success(data = games))
                }
            }catch (e: Exception){
                emit(UserUiState.Failed(errorMessage = e.message))
            }
        }.flowOn(Dispatchers.IO)

}