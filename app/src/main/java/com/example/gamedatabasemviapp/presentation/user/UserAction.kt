package com.example.gamedatabasemviapp.presentation.user

internal sealed class UserAction {
    data class FetchGamesAction(val queryGame: String): UserAction()
}