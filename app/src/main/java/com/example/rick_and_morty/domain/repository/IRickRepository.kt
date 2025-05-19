package com.example.rick_and_morty.domain.repository

import com.example.rick_and_morty.domain.entity.CharacterEntity

interface IRickRepository {
    suspend fun getAllCharacters(forceRefresh: Boolean = false): List<CharacterEntity>
    suspend fun getCharacterById(id: Int, forceRefresh: Boolean = false): CharacterEntity
}