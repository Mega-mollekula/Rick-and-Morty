package com.example.rick_and_morty.data.service

import androidx.room.Dao
import androidx.room.Query
import com.example.rick_and_morty.data.model.CharacterModel

@Dao
interface CharactersDAO {
    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterModel>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharactersById(id: String): CharacterModel
}