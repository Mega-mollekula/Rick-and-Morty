package com.example.rick_and_morty.data.repository

import com.example.rick_and_morty.data.mapper.CharacterMapper
import com.example.rick_and_morty.data.service.CharactersDAO
import com.example.rick_and_morty.data.service.RickApiService
import com.example.rick_and_morty.domain.entity.CharacterEntity
import com.example.rick_and_morty.domain.repository.IRickRepository

class RickRepository(
    private val apiService: RickApiService,
    private val dao: CharactersDAO
):  IRickRepository {
    override suspend fun getAllCharacters(forceRefresh: Boolean): List<CharacterEntity> {
        val localData = dao.getAllCharacters()
        if (localData.isEmpty() || forceRefresh){
            val remoteData = apiService.getAllCharacters()
            //save remote data to local database
            return remoteData.results.map { CharacterMapper.mapDTOtoEntity(it) }
        }
        return localData.map {CharacterMapper.mapModelToEntity(it)}
    }
}