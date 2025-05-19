package com.example.rick_and_morty.common.database

import android.content.Context
import androidx.room.Room
import com.example.rick_and_morty.data.service.CharactersDAO

object DatabaseModule {
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    fun provideCharactersDao(db: AppDatabase): CharactersDAO {
        return db.characterDAO()
    }
}
