package com.example.rick_and_morty.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rick_and_morty.data.model.CharacterModel
import com.example.rick_and_morty.data.service.CharactersDAO

@Database(
    entities = [CharacterModel::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDAO(): CharactersDAO

    companion object {
        const val DATABASE_NAME = "characters.db"
    }
}
