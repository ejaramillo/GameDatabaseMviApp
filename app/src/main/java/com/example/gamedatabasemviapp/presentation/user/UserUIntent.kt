package com.example.gamedatabasemviapp.presentation.user


internal sealed class UserUIntent {
    object InitialUIntent : UserUIntent()
    object PressButtonUIntent : UserUIntent()
}
