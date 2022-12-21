package com.example.gamedatabasemviapp.presentation.user

sealed class UserAction {
    data class FetchGamesAction(val queryGame: String): UserAction()
}