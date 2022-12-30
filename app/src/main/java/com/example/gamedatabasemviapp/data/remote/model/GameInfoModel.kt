package com.example.gamedatabasemviapp.data.remote.model

import com.example.gamedatabasemviapp.data.remote.Constants
import com.example.gamedatabasemviapp.data.remote.Constants.ID
import com.google.gson.annotations.SerializedName

data class GameInfoModel(
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
