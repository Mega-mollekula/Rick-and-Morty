package com.example.rick_and_morty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.rick_and_morty.common.database.DatabaseModule
import com.example.rick_and_morty.data.repository.RickRepository
import com.example.rick_and_morty.data.service.RickApiService
import com.example.rick_and_morty.presentation.screen.CharacterScreen
import com.example.rick_and_morty.presentation.viewmodel.CharacterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val db = DatabaseModule.provideDatabase(context)
            val dao = DatabaseModule.provideCharactersDao(db)
            val repository = RickRepository(RickApiService, dao)
            val viewModel = remember { CharacterViewModel(repository) }

            CharacterScreen(viewModel = viewModel)
        }
    }
}

