package com.example.a3_133.ui.view.pemasok

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.R
import com.example.a3_133.model.Pemasok
import com.example.a3_133.model.Produk
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.view.merk.MerkLayout
import com.example.a3_133.ui.view.merk.OnError
import com.example.a3_133.ui.view.merk.OnLoading
import com.example.a3_133.ui.viewmodel.pemasok.HomePemasokUiState
import com.example.a3_133.ui.viewmodel.pemasok.HomePemasokViewModel
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.merk.HomeUiState

object DestinasiHomePemasok : DestinasiNavigasi {
    override val route = "homepemasok"
    override val titleRes = "Home Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemasokHomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToUpdate: (String) -> Unit,
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomePemasokViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getPemasok()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHomePemasok.titleRes,
                canNavigateBack = true,
                onRefresh = {
                    viewModel.getPemasok()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFFFF9900),
                contentColor = Color.White,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pemasok")
                Text(text = " Tambah Pemasok")
            }
        },
    ) { innerPadding ->
        PemasokHomeStatus(
            homePemasokUiState = viewModel.pemasokUIState,
            retryAction = { viewModel.getPemasok() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = { pemasok ->
                viewModel.deletePemasok(pemasok.idPemasok)
            },
            onUpdateClick = { pemasok ->
                navigateToUpdate(pemasok.idPemasok)
            },
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun PemasokHomeStatus(
    homePemasokUiState: HomePemasokUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pemasok) -> Unit,
    onDetailClick: (String) -> Unit,
    onUpdateClick: (Pemasok) -> Unit
) {
    when (homePemasokUiState) {
        is HomePemasokUiState.Loading -> OnLoadingPemasok(modifier = modifier.fillMaxSize())
        is HomePemasokUiState.Success ->
            if (homePemasokUiState.pemasok.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Pemasok")
                }
            } else {
                PemasokLayout(
                    pemasok = homePemasokUiState.pemasok,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { pemasok -> onDetailClick(pemasok.idPemasok) },
                    onDeleteClick = onDeleteClick,
                    onUpdateClick = onUpdateClick
                )
            }
        is HomePemasokUiState.Error -> OnErrorPemasok(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoadingPemasok(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorPemasok(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9900),
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PemasokLayout(
    pemasok: List<Pemasok>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pemasok) -> Unit,
    onDeleteClick: (Pemasok) -> Unit,
    onUpdateClick: (Pemasok) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pemasok) { pmk ->
            PemasokCard(
                pemasok = pmk,
                modifier = Modifier.fillMaxWidth(),
                onDeleteClick = { onDeleteClick(pmk) },
                onDetailClick = { onDetailClick(pmk) },
                onUpdateClick = { onUpdateClick(pmk) }
            )
        }
    }
}

@Composable
fun PemasokCard(
    pemasok: Pemasok,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pemasok) -> Unit = {},
    onUpdateClick: (Pemasok) -> Unit = {},
    onDetailClick: (Pemasok) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text(text = "Apakah Anda yakin ingin menghapus data pemasok ini?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDeleteClick(pemasok)
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Card(
        modifier = modifier
            .clickable { onDetailClick(pemasok) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
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
                    text = pemasok.namaPemasok,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFFF9900)
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFFF9900)
                    )
                }

                IconButton(onClick = { onUpdateClick(pemasok) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color(0xFFFF9900)
                    )
                }
            }

            Text(
                text = pemasok.alamatPemasok,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}