package com.example.gamedatabasemviapp.presentation.user

import com.example.gamedatabasemviapp.data.model.GameInfoModel

internal sealed class UserResult {
    sealed class FetchGamesResult : UserResult() {
        object InProgress : FetchGamesResult()
        data class Success(val games: List<String>) : FetchGamesResult()
        object Error : FetchGamesResult()
        object Empty : FetchGamesResult()
    }
}