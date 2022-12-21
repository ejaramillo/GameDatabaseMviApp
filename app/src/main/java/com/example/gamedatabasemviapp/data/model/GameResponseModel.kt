package com.example.gamedatabasemviapp.data.model

import com.google.gson.annotations.SerializedName

data class GameResponseModel(
    @SerializedName("results")
    val gameDataResults: List<GameInfoModel>?
)
