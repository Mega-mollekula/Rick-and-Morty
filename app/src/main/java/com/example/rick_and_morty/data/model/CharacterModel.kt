package com.example.rick_and_morty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//то, что сохраняется в бд
@Entity(tableName = "characters")
data class CharacterModel (
    @PrimaryKey val id: Int,
    val name: String,
    val age: Int,
    val image: String,
    val status: String
)