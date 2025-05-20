package com.example.rick_and_morty.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rick_and_morty.domain.entity.CharacterEntity
import com.example.rick_and_morty.presentation.viewmodel.CharacterViewModel
import com.example.rick_and_morty.presentation.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(viewModel: CharacterViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val page by viewModel.currentPage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Characters – page ${page + 1}") })
        }
    ) { padding ->

        Box(Modifier.fillMaxSize().padding(padding)) {

            when (uiState) {

                UiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                UiState.Empty -> Text("Нет данных", Modifier.align(Alignment.Center))

                UiState.Error -> Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Произошла ошибка.")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadCharacters(forceRefresh = true) }) {
                        Text("Повторить")
                    }
                }
                is UiState.Success -> {
                    val pageCharacters by viewModel.pagedCharacters.collectAsState()
                    Column(Modifier.fillMaxSize()) {
                        CharacterList(
                            characters = pageCharacters,
                            modifier = Modifier.weight(1f)
                        )
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { viewModel.previousPage() },
                                enabled = page > 0
                            ) {
                                Text("<")
                            }
                            Button(
                                onClick = { viewModel.nextPage() },
                                enabled = (page + 1) * 20 < (uiState as UiState.Success).characters.size
                            ) {
                                Text(">")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterList(characters: List<CharacterEntity>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characters) { c ->
            Card(
                Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(Modifier.padding(8.dp)) {
                    AsyncImage(
                        model = c.image,
                        contentDescription = c.name,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(c.name, fontWeight = FontWeight.Bold)
                        Text("Status: ${c.status}")
                        Text("Species: ${c.species}")
                    }
                }
            }
        }
    }
}
