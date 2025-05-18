package com.example.rick_and_morty.presentation.viewmodel

import com.example.rick_and_morty.domain.entity.CharacterEntity
import com.example.rick_and_morty.domain.repository.IRickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterViewModel(
    private val repository: IRickRepository,
) {
    private val _characters = MutableStateFlow(emptyList<CharacterEntity>())

    val characters: StateFlow<List<CharacterEntity>> = _characters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    suspend fun loadCharacters(forceRefresh: Boolean = false) {
        _isLoading.value = true
        _isError.value = false
        try {
            val characterList = repository.getAllCharacters(forceRefresh)
            _characters.value = characterList
        } catch (e: Exception) {
            _isError.value = true
        } finally {
            _isLoading.value = false
        }
    }
}