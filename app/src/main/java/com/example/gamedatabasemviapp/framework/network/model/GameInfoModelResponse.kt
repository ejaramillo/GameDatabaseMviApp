package com.example.gamedatabasemviapp.framework.network.model

import com.example.gamedatabasemviapp.data.Constants.ID
import com.google.gson.annotations.SerializedName

data class GameInfoModelResponse(
    @SerializedName(ID)
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("released")
    val released: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("rating")
    val rating: Double?
)
