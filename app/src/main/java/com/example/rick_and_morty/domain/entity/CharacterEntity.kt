package com.example.rick_and_morty.domain.entity

import android.net.Uri

data class CharacterEntity (
    val name: String,
    val image: Uri,
    val status: String,
    val species: String
)