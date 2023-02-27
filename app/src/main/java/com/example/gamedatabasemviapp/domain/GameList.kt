package com.example.gamedatabasemviapp.domain

import com.google.gson.annotations.SerializedName

data class GameList(
    @SerializedName("results")
    val gameDataResults: List<GameInfoModel>?
)
