package com.example.gamedatabasemviapp.presentation.user

import com.example.gamedatabasemviapp.domain.GameList

internal sealed class UserUiState {
    object LoadingUiState : UserUiState()
    object DefaultUiState : UserUiState()
    object EmptyUiState : UserUiState()
    data class ListGameUiState(val data: GameList) : UserUiState()
    data class FailedUiState(val errorMessage: String) : UserUiState()


}
