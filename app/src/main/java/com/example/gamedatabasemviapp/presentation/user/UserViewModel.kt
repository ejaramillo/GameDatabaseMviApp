package com.example.gamedatabasemviapp.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamedatabasemviapp.data.MainRepository
import com.example.gamedatabasemviapp.data.model.GameInfoModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    val queryGameIntent = Channel<UserAction>(Channel.UNLIMITED)
    private val _queryGameState = MutableStateFlow<UserUiState<List<GameInfoModel>>>(UserUiState.Idle)
    val queryGameState: StateFlow<UserUiState<List<GameInfoModel>>>
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
            if (queryGame.isEmpty()) {
                _queryGameState.value = UserUiState.Loading
                mainRepository.searchGames(queryGame).collect {
                    _queryGameState.value = it
                }
            }
        }
    }

}