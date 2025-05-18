package com.example.rick_and_morty.data.dto

import kotlinx.serialization.Serializable

//полное покрытие того, что получаем с сервера (в идеале - все, но тут для упрощения не все)
@Serializable
data class CharacterDTO (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)