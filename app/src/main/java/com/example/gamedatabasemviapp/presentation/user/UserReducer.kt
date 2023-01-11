package com.example.gamedatabasemviapp.presentation.user

import com.example.gamedatabasemviapp.presentation.user.UserUiState.DefaultUiState
import com.example.gamedatabasemviapp.presentation.user.UserUiState.EmptyUiState
import com.example.gamedatabasemviapp.presentation.user.UserUiState.FailedUiState
import com.example.gamedatabasemviapp.presentation.user.UserUiState.ListGameUiState
import com.example.gamedatabasemviapp.presentation.user.UserUiState.LoadingUiState

internal class UserReducer {

    infix fun UserUiState.reduceWith(result: UserResult): UserUiState {
        return when (val previousState = this) {
            is DefaultUiState -> previousState reduceWith result
            is LoadingUiState -> previousState reduceWith result
            is ListGameUiState -> previousState reduceWith result
            is EmptyUiState -> previousState reduceWith result
            is FailedUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.FetchGamesResult.Success -> ListGameUiState(
            data = result.data
        )
        is UserResult.FetchGamesResult.Error -> FailedUiState(
            errorMessage = result.errorMessage
        )
        is UserResult.FetchGamesResult.Empty -> EmptyUiState
        else -> throw unsupportedReduceCase()
    }

    private infix fun ListGameUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.FetchGamesResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }

    private infix fun FailedUiState.reduceWith(result: UserResult) = when(result){
        is UserResult.FetchGamesResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }

    private infix fun EmptyUiState.reduceWith(result: UserResult) = when(result){
        is UserResult.FetchGamesResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }

    private infix fun DefaultUiState.reduceWith(result: UserResult) = when(result){
        is UserResult.FetchGamesResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }

    private fun unsupportedReduceCase() = RuntimeException()

}