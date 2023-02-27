package com.example.gamedatabasemviapp.presentation.user

import com.example.gamedatabasemviapp.domain.GameList

internal sealed class UserResult {
    sealed class FetchGamesResult : UserResult() {
        object InProgress : FetchGamesResult()
        data class Success(val data: GameList) : FetchGamesResult()
        data class Error(val errorMessage: String) : FetchGamesResult()
        object Empty : FetchGamesResult()
    }
}