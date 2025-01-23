package com.example.a3_133.ui.view.merk

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
import com.example.a3_133.model.Merk
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
                canNavigateBack = false,
                onRefresh = {
                    viewModel.getMerk()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Merk")
                Text(text = "Tambah Merk")
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
            }
        )
    }
}

@Composable
fun MerkHomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Merk) -> Unit,
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
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun MerkLayout(
    merk: List<Merk>,
    modifier: Modifier = Modifier,
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
    onUpdateClick: (Merk) -> Unit = {}
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
                    text = merk.namaMerk,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(merk) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }

                IconButton(onClick = { onUpdateClick(merk) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Merk",
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