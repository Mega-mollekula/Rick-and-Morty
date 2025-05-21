package com.example.rick_and_morty.data.service

import com.example.rick_and_morty.common.api.NetworkModule
import com.example.rick_and_morty.data.dto.CharacterDTO
import com.example.rick_and_morty.data.dto.CharacterListDTO
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object RickApiService {
    private const val BASE_URL = "https://rickandmortyapi.com/api"

    /* функция такая странная, потому что api возвращает по запросу к end-point /character только первые 20 персонажей */
    suspend fun getAllCharacters(): List<CharacterDTO> = coroutineScope {
        val firstPage = NetworkModule.publicClient.get("$BASE_URL/character").body<CharacterListDTO>()
        val totalPages = firstPage.info.pages
        
        val allCharacters = mutableListOf<CharacterDTO>()
        allCharacters.addAll(firstPage.results)
        
        val remainingPages = (2..totalPages).map { page ->
            async {
                try {
                    NetworkModule.publicClient.get("$BASE_URL/character?page=$page").body<CharacterListDTO>().results
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }
        remainingPages.forEach { deferred ->
            allCharacters.addAll(deferred.await())
        }
        allCharacters
    }

    suspend fun getCharacterById(id: Int): CharacterDTO {
        return NetworkModule.publicClient.get("$BASE_URL/character/$id").body()
    }
}