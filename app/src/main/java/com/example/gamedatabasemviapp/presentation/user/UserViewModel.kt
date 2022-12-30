package com.example.gamedatabasemviapp.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gamedatabasemviapp.data.MainRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

internal class UserViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    val queryGameIntent = Channel<UserAction>(Channel.UNLIMITED)
    private val _queryGameState = MutableStateFlow<UserUiState>(UserUiState.DefaultUiState)
    val queryGameState: StateFlow<UserUiState>
        get() = _queryGameState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            queryGameIntent.consumeAsFlow().collect {
                when (it) {
                    is UserAction.FetchGamesAction -> searchGame(it.queryGame)
                }
            }
        }
    }

    private fun searchGame(queryGame: String) {
        viewModelScope.launch {
            if (queryGame.isEmpty()){
                _queryGameState.value = UserUiState.EmptyUiState
            } else {
                _queryGameState.value = UserUiState.LoadingUiState
                mainRepository.searchGames(queryGame).collect {userUiState ->
                    _queryGameState.value = userUiState
                }
            }
        }
    }
}

internal class UserViewModelFactory(private val mainRepository: MainRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(mainRepository) as T
    }
}