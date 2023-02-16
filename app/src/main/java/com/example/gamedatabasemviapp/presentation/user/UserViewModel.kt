package com.example.gamedatabasemviapp.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gamedatabasemviapp.data.datasource.RemoteDataSource
import com.example.gamedatabasemviapp.data.repository.NetworkRepository
import com.example.gamedatabasemviapp.framework.network.NetworkRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import okhttp3.internal.userAgent

@FlowPreview
@ExperimentalCoroutinesApi
internal class UserViewModel constructor(
    val processor: UserProcessor
) : ViewModel() {

    private val reducer = UserReducer()

    val defaultUiState: UserUiState = UserUiState.DefaultUiState
    private val _uiState: MutableStateFlow<UserUiState> = MutableStateFlow(defaultUiState)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun processUserIntentsAndObserveUiStates(
        userIntents: Flow<UserUIntent>,
        coroutineScope: CoroutineScope = viewModelScope,
    ) {
        userIntents.buffer()
            .flatMapMerge() { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .scan(defaultUiState) { previousUiState,
                                    result ->
                with(reducer) {
                    previousUiState reduceWith result
                }

            }
            .onEach {
                _uiState.value = it
            }
            .launchIn(coroutineScope)
    }

    private fun UserUIntent.toAction(): UserAction {
        return when (this) {
            is UserUIntent.SearchGameEdtChangeUIntent -> UserAction.FetchGamesAction(searchText)
        }
    }
}

internal class UserViewModelFactory(userProcessor: UserProcessor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel() as T
    }
}

