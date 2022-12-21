package com.example.gamedatabasemviapp.data.model

import com.google.gson.annotations.SerializedName

data class GameInfoModel(
    @SerializedName("id")
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
