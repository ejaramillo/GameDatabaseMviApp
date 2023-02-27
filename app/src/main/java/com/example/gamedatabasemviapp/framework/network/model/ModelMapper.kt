package com.example.gamedatabasemviapp.framework.network.model

import com.example.gamedatabasemviapp.domain.GameInfoModel
import com.example.gamedatabasemviapp.domain.GameList

fun GameInfoModelResponse.toGameInfoModel() = GameInfoModel(
    id = id,
    name = name,
    released=released,
    backgroundImage=backgroundImage,
    rating=rating
)

fun GameListResponse.toGameList() = GameList(
    gameDataResults = gameDataResults?.map { gameInfoModelResponse ->
        gameInfoModelResponse.toGameInfoModel()
    }
)