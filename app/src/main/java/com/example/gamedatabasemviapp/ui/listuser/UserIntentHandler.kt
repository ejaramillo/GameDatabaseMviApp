package com.example.gamedatabasemviapp.ui.listuser

import com.example.gamedatabasemviapp.presentation.user.UserUIntent
import com.example.gamedatabasemviapp.presentation.user.UserUIntent.InitialUIntent
import com.example.gamedatabasemviapp.presentation.user.UserUIntent.PressButtonUIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class UserIntentHandler {
    private val userIntents = MutableSharedFlow<UserUIntent>()
    val coroutineScope: CoroutineScope? = null

    fun initialUIntent() {
        coroutineScope?.launch {
            userIntents.emit(InitialUIntent)
        }
    }

    fun pressButtonUIntent() {
        coroutineScope?.launch {
            userIntents.emit(PressButtonUIntent)
        }
    }
}