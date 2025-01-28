package com.example.a3_133.ui.view.merk

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
import com.example.a3_133.model.Merk
import com.example.a3_133.model.Pemasok
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.merk.HomeMerkViewModel
import com.example.a3_133.ui.viewmodel.merk.HomeUiState
import com.example.a3_133.ui.viewmodel.PenyediaViewModel

object DestinasiHome : DestinasiNavigasi {
    override val route = "homemerk"
    override val titleRes = "Home Merk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MerkHomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToUpdate: (String) -> Unit,
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeMerkViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getMerk()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = true,
                onRefresh = {
                    viewModel.getMerk()
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Merk")
                Text(text = " Tambah Merk")
            }
        },
    ) { innerPadding ->
        MerkHomeStatus(
            homeUiState = viewModel.merkUIState,
            retryAction = { viewModel.getMerk() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = { merk ->
                viewModel.deleteMerk(merk.idMerk)
            },
            onUpdateClick = { merk ->
                navigateToUpdate(merk.idMerk)
            },
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun MerkHomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Merk) -> Unit,
    onDetailClick: (String) -> Unit,
    onUpdateClick: (Merk) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (homeUiState.merk.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Merk")
                }
            } else {
                MerkLayout(
                    merk = homeUiState.merk,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { merk -> onDetailClick(merk.idMerk) },
                    onDeleteClick = onDeleteClick,
                    onUpdateClick = onUpdateClick
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier){
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
fun MerkLayout(
    merk: List<Merk>,
    modifier: Modifier = Modifier,
    onDetailClick: (Merk) -> Unit,
    onDeleteClick: (Merk) -> Unit,
    onUpdateClick: (Merk) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(merk) { mrk ->
            MerkCard(
                merk = mrk,
                modifier = Modifier.fillMaxWidth(),
                onDeleteClick = { onDeleteClick(mrk) },
                onDetailClick = { onDetailClick(mrk) },
                onUpdateClick = { onUpdateClick(mrk) }
            )
        }
    }
}

@Composable
fun MerkCard(
    merk: Merk,
    modifier: Modifier = Modifier,
    onDeleteClick: (Merk) -> Unit = {},
    onUpdateClick: (Merk) -> Unit = {},
    onDetailClick: (Merk) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text(text = "Apakah Anda yakin ingin menghapus data merk ini?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDeleteClick(merk)
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
            .clickable { onDetailClick(merk) },
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
                    text = merk.namaMerk,
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

                IconButton(onClick = { onUpdateClick(merk) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color(0xFFFF9900)
                    )
                }
            }

            Text(
                text = merk.deskripsiMerk,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}