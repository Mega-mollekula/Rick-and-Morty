package com.example.rick_and_morty.common.database

import androidx.room.Database
import com.example.rick_and_morty.data.model.CharacterModel
import com.example.rick_and_morty.data.service.CharactersDAO

@Database(
    entities = [CharacterModel::class],
    version = 1,
    exportSchema = false,
)
abstract class Database {
    abstract fun characterDAO(): CharactersDAO
    companion object {
        const val DATABASE_NAME = "characters.db"
    }
}

