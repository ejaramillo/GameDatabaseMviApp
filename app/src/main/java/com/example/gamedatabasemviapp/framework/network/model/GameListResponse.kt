package com.example.gamedatabasemviapp.framework.network.model

import com.google.gson.annotations.SerializedName

data class GameListResponse(
    @SerializedName("results")
    val gameDataResults: List<GameInfoModelResponse>?
)
