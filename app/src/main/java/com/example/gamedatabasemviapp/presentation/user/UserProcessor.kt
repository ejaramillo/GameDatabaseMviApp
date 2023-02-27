package com.example.gamedatabasemviapp.presentation.user

import com.example.gamedatabasemviapp.data.repository.NetworkRepository
import com.example.gamedatabasemviapp.presentation.user.UserResult.FetchGamesResult
import com.example.gamedatabasemviapp.presentation.user.UserResult.FetchGamesResult.Empty
import com.example.gamedatabasemviapp.presentation.user.UserResult.FetchGamesResult.Error
import com.example.gamedatabasemviapp.presentation.user.UserResult.FetchGamesResult.InProgress
import com.example.gamedatabasemviapp.presentation.user.UserResult.FetchGamesResult.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


internal class UserProcessor(val repository: NetworkRepository) {

    fun actionProcessor(actions: UserAction): Flow<UserResult> =
        when (actions) {
            is UserAction.FetchGamesAction -> loadGamesProcessor(actions.queryGame)
        }

    private  fun loadGamesProcessor(queryGame: String): Flow<FetchGamesResult> =
        repository.searchGames(queryGame)
            .map { response ->
                if(response.gameDataResults?.isEmpty()?:true){
                    Empty
                } else{
                    Success(data = response)
                }
            }.onStart {
                emit(InProgress)
            }.catch {e ->
                emit(Error(errorMessage = e?.message.orEmpty()))
            }.flowOn(Dispatchers.IO)
}