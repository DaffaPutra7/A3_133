package com.example.a3_133.ui.view.kategori

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.R
import com.example.a3_133.model.Kategori
import com.example.a3_133.model.Merk
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.kategori.HomeKategoriUiState
import com.example.a3_133.ui.viewmodel.kategori.HomeKategoriViewModel
import com.example.a3_133.ui.viewmodel.PenyediaViewModel

object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "homekategori"
    override val titleRes = "Home Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KategoriHomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToUpdate: (String) -> Unit,
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getKategori()
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFA500), Color(0xFFFF4500))
                )
            ),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHomeKategori.titleRes,
                canNavigateBack = true,
                onRefresh = {
                    viewModel.getKategori()
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kategori")
                Text(text = " Tambah Kategori")
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
            },
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun KategoriHomeStatus(
    homeKategoriUiState: HomeKategoriUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit,
    onDetailClick: (String) -> Unit,
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
                    onDetailClick = { kategori -> onDetailClick(kategori.idKategori) },
                    onDeleteClick = onDeleteClick,
                    onUpdateClick = onUpdateClick
                )
            }
        is HomeKategoriUiState.Error -> OnErrorKategori(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoadingKategori(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorKategori(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
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
fun KategoriLayout(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kategori) -> Unit,
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
                onDetailClick = { onDetailClick(kt) },
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
    onUpdateClick: (Kategori) -> Unit = {},
    onDetailClick: (Kategori) -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable { onDetailClick(kategori) }
            .padding(vertical = 8.dp), // Menambah padding vertikal untuk ruang yang lebih baik
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
                    text = kategori.namaKategori,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFFF9900),
                    maxLines = 2, // Mengizinkan teks dilanjutkan ke baris berikutnya
                    overflow = TextOverflow.Ellipsis, // Memotong teks dengan elipsis jika terlalu panjang
                    modifier = Modifier.weight(1f) // Mengatur lebar teks
                )
                IconButton(onClick = { onDeleteClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Kategori",
                        tint = Color(0xFFFF9900)
                    )
                }

                IconButton(onClick = { onUpdateClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Kategori",
                        tint = Color(0xFFFF9900)
                    )
                }
            }

            Text(
                text = "Deskripsi : ${ kategori.deskripsiKategori}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}