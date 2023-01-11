package com.example.gamedatabasemviapp.ui.listuser

import com.example.gamedatabasemviapp.presentation.user.UserUIntent
import com.example.gamedatabasemviapp.presentation.user.UserUIntent.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
internal class UserIntentHandler {
    private val userIntents = MutableSharedFlow<UserUIntent>()
    var coroutineScope: CoroutineScope? = null

    internal fun userIntents(): Flow<UserUIntent> = userIntents.asSharedFlow()

    fun getListGameUIntent(seachText: String){
        coroutineScope?.launch {
            userIntents.emit(SearchGameEdtChangeUIntent(seachText))
        }
    }
}