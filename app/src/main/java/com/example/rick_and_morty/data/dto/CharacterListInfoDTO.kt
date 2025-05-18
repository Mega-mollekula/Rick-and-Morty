package com.example.rick_and_morty.data.dto

import kotlinx.serialization.Serializable

@Serializable
class CharacterListInfoDTO (
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
