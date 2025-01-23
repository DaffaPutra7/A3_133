package com.example.a3_133.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.R
import com.example.a3_133.model.Kategori
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.HomeKategoriUiState
import com.example.a3_133.ui.viewmodel.HomeKategoriViewModel
import com.example.a3_133.ui.viewmodel.PenyediaViewModel

object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KategoriHomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getKategori()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHomeKategori.titleRes,
                canNavigateBack = false,
                onRefresh = {
                    viewModel.getKategori()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kategori")
                Text(text = "Tambah Kategori")
            }
        },
    ) { innerPadding ->
        KategoriHomeStatus(
            homeKategoriUiState = viewModel.kategoriUIState,
            retryAction = { viewModel.getKategori() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = { kategori ->
                viewModel.deleteKategori(kategori.idKategori)
            },
            onUpdateClick = { kategori ->
                navigateToUpdate(kategori.idKategori)
            }
        )
    }
}

@Composable
fun KategoriHomeStatus(
    homeKategoriUiState: HomeKategoriUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit,
    onUpdateClick: (Kategori) -> Unit
) {
    when (homeKategoriUiState) {
        is HomeKategoriUiState.Loading -> OnLoadingKategori(modifier = modifier.fillMaxSize())
        is HomeKategoriUiState.Success ->
            if (homeKategoriUiState.kategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Kategori")
                }
            } else {
                KategoriLayout(
                    kategori = homeKategoriUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick,
                    onUpdateClick = onUpdateClick
                )
            }
        is HomeKategoriUiState.Error -> OnErrorKategori(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoadingKategori(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorKategori(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun KategoriLayout(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit,
    onUpdateClick: (Kategori) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kategori) { kt ->
            KategoriCard(
                kategori = kt,
                modifier = Modifier.fillMaxWidth(),
                onDeleteClick = { onDeleteClick(kt) },
                onUpdateClick = { onUpdateClick(kt) }
            )
        }
    }
}

@Composable
fun KategoriCard(
    kategori: Kategori,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit = {},
    onUpdateClick: (Kategori) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = kategori.namaKategori,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }

                IconButton(onClick = { onUpdateClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                    )
                }
            }

            Text(
                text = kategori.deskripsiKategori,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}