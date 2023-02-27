package com.example.gamedatabasemviapp.domain

import com.example.gamedatabasemviapp.data.Constants.ID
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
