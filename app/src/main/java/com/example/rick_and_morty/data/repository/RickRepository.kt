package com.example.rick_and_morty.data.repository

import com.example.rick_and_morty.data.mapper.CharacterMapper
import com.example.rick_and_morty.data.service.CharactersDAO
import com.example.rick_and_morty.data.service.RickApiService
import com.example.rick_and_morty.domain.entity.CharacterEntity
import com.example.rick_and_morty.domain.repository.IRickRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RickRepository(
    private val apiService: RickApiService,
    private val dao: CharactersDAO
): IRickRepository {
    override suspend fun getAllCharacters(forceRefresh: Boolean): List<CharacterEntity> = withContext(Dispatchers.IO) {
        try {
            val localData = dao.getAllCharacters()
            if (localData.isEmpty() || forceRefresh) {
                try {
                    val remoteData = apiService.getAllCharacters()
                    val models = remoteData.map { CharacterMapper.mapDTOtoModel(it) }

                    dao.clearCharacters()
                    dao.insertAll(models)

                    return@withContext models.map { CharacterMapper.mapModelToEntity(it) }
                } catch (e: ClientRequestException) {
                    // Ошибка клиента (4xx)
                    throw Exception("Ошибка запроса: ${e.message}")
                } catch (e: ServerResponseException) {
                    // Ошибка сервера (5xx)
                    throw Exception("Ошибка сервера: ${e.message}")
                } catch (e: Exception) {
                    // Другие ошибки
                    throw Exception("Ошибка сети: ${e.message}")
                }
            }
            return@withContext localData.map { CharacterMapper.mapModelToEntity(it) }
        } catch (e: Exception) {
            throw Exception("Ошибка при получении данных: ${e.message}")
        }
    }

    override suspend fun getCharacterById(id: Int, forceRefresh: Boolean): CharacterEntity = withContext(Dispatchers.IO) {
        try {
            val localCharacter = dao.getCharacterById(id.toString())
            if (localCharacter == null || forceRefresh) {
                try {
                    val remoteCharacterDTO = apiService.getCharacterById(id)
                    val model = CharacterMapper.mapDTOtoModel(remoteCharacterDTO)
                    dao.insertCharacter(model)

                    return@withContext CharacterMapper.mapModelToEntity(model)
                } catch (e: ClientRequestException) {
                    throw Exception("Ошибка запроса: ${e.message}")
                } catch (e: ServerResponseException) {
                    throw Exception("Ошибка сервера: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Ошибка сети: ${e.message}")
                }
            }
            return@withContext CharacterMapper.mapModelToEntity(localCharacter)
        } catch (e: Exception) {
            throw Exception("Ошибка при получении данных: ${e.message}")
        }
    }
}