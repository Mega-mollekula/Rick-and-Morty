package com.example.rick_and_morty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rick_and_morty.domain.entity.CharacterEntity
import com.example.rick_and_morty.domain.repository.IRickRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class UiState {
    data object Loading : UiState()
    data object Error : UiState()
    data object Empty : UiState()
    data class Success(val characters: List<CharacterEntity>) : UiState()
}

class CharacterViewModel(
    private val repository: IRickRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val pageSize = 20
    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _allCharacters = MutableStateFlow<List<CharacterEntity>>(emptyList())

    val pagedCharacters: StateFlow<List<CharacterEntity>> = combine(
        _allCharacters, _currentPage
    ) { characters, page ->
        val from = page * pageSize
        val to = minOf(from + pageSize, characters.size)
        if (from >= characters.size) emptyList()
        else characters.subList(from, to)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun nextPage() {
        if ((_currentPage.value + 1) * pageSize < _allCharacters.value.size) {
            _currentPage.value += 1
        }
    }

    fun previousPage() {
        if (_currentPage.value > 0) {
            _currentPage.value -= 1
        }
    }

    fun loadCharacters(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val characters = repository.getAllCharacters(forceRefresh)
                if (characters.isEmpty()) {
                    _uiState.value = UiState.Empty
                } else {
                    _allCharacters.value = characters
                    _uiState.value = UiState.Success(characters)
                }
            } catch (e: Exception) {
                try {
                    val cached = repository.getAllCharacters(forceRefresh = false)
                    if (cached.isNotEmpty()) {
                        _allCharacters.value = cached
                        _uiState.value = UiState.Success(cached)
                    } else {
                        _uiState.value = UiState.Error
                    }
                } catch (ex: Exception) {
                    _uiState.value = UiState.Error
                }
            }
        }
    }

    init {
        loadCharacters(forceRefresh = true)
    }
}
