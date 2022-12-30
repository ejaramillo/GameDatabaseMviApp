package com.example.gamedatabasemviapp.presentation.user

import com.example.gamedatabasemviapp.data.remote.model.GameResponseModel

internal sealed class UserUiState {
    object LoadingUiState : UserUiState()
    object DefaultUiState : UserUiState()
    object EmptyUiState : UserUiState()
    data class ListGameUiState(val data: GameResponseModel) : UserUiState()
    data class FailedUiState(val errorMessage: String) : UserUiState()


}
