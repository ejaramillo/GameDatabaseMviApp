package com.example.gamedatabasemviapp.presentation.user

sealed class UserUiState<out T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    object Loading : UserUiState<Nothing>()
    object Idle : UserUiState<Nothing>()
    object Empty : UserUiState<Nothing>()
    class Success<T>(data: T?) : UserUiState<T>(
        data = data
    )
    class Failed(errorMessage: String?): UserUiState<Nothing>(
        errorMessage =errorMessage
    )
}
