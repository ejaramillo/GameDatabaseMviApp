package com.example.gamedatabasemviapp.presentation.user


internal sealed class UserUIntent {
    data class SearchGameEdtChangeUIntent(val searchText: String) : UserUIntent()
}
