package com.example.rick_and_morty.data.service

import com.example.rick_and_morty.common.api.NetworkModule
import com.example.rick_and_morty.data.dto.CharacterListDTO
import io.ktor.client.call.body
import io.ktor.client.request.get

object RickApiService {
    private const val BASE_URL = "https://rickandmortyapi.com/api"

    suspend fun getAllCharacters(): CharacterListDTO{
        return NetworkModule.publicClient.get("$BASE_URL/character").body()
    }

    suspend fun getCharacterById(id: Int): CharacterListDTO{
        return NetworkModule.publicClient.get("$BASE_URL/character/$id").body()
    }
}