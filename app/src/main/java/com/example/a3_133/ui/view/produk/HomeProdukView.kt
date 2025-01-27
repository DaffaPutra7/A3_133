package com.example.a3_133.ui.view.produk

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
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.R
import com.example.a3_133.model.Merk
import com.example.a3_133.model.Produk
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.customwidget.Footbar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.produk.HomeProdukUiState
import com.example.a3_133.ui.viewmodel.produk.HomeProdukViewModel

object DestinasiHomeProduk : DestinasiNavigasi {
    override val route = "homeproduk"
    override val titleRes = "Home Produk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProdukScreen(
    navigateToItemEntry: () -> Unit,
    navigateToUpdate: (String) -> Unit,
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit,
    onProdukClick: () -> Unit,
    onPemasokClick: () -> Unit,
    onMerkClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeProdukViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getProduk()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHomeProduk.titleRes,
                canNavigateBack = true,
                onRefresh = {
                    viewModel.getProduk()
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Produk")
                Text(text = " Tambah Produk")
            }
        },
        bottomBar = {
            Footbar(
                onHalamanProduk = onProdukClick,
                onHalamanPemasok = onPemasokClick,
                onHalamanMerk = onMerkClick
            )
        }
    ) { innerPadding ->
        HomeProdukStatus(
            homeProdukUiState = viewModel.produkUiState,
            retryAction = { viewModel.getProduk() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                viewModel.deleteProduk(it.idProduk)
                viewModel.getProduk()
            },
            onUpdateClick = { produk ->
                navigateToUpdate(produk.idProduk)
            },
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeProdukStatus(
    homeProdukUiState: HomeProdukUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Produk) -> Unit = {},
    onDetailClick: (String) -> Unit,
    onUpdateClick: (Produk) -> Unit
) {
    when (homeProdukUiState) {
        is HomeProdukUiState.Loading -> OnLoadingProduk(modifier = modifier.fillMaxSize())

        is HomeProdukUiState.Success ->
            if (homeProdukUiState.produk.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Produk")
                }
            } else {
                ProdukLayout(
                    produk = homeProdukUiState.produk,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { produk -> onDetailClick(produk.idProduk) },
                    onDeleteClick = { produk -> onDeleteClick(produk) },
                    onUpdateClick = onUpdateClick
                )
            }
        is HomeProdukUiState.Error -> OnErrorProduk(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoadingProduk(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorProduk(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
fun ProdukLayout(
    produk: List<Produk>,
    modifier: Modifier = Modifier,
    onDetailClick: (Produk) -> Unit,
    onDeleteClick: (Produk) -> Unit = {},
    onUpdateClick: (Produk) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(produk) { pr ->
            ProdukCard(
                produk = pr,
                modifier = Modifier.fillMaxWidth(),
                onDeleteClick = { onDeleteClick(pr) },
                onDetailClick = { onDetailClick(pr) },
                onUpdateClick = { onUpdateClick(pr) }
            )
        }
    }
}

@Composable
fun ProdukCard(
    produk: Produk,
    modifier: Modifier = Modifier,
    onDeleteClick: (Produk) -> Unit = {},
    onUpdateClick: (Produk) -> Unit = {},
    onDetailClick: (Produk) -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onDetailClick(produk) }, // Pastikan seluruh card dapat diklik untuk melihat detail
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top // Mengatur icon button di bagian atas
            ) {
                Text(
                    text = produk.namaProduk,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFFF9900),
                    maxLines = 2, // Mengizinkan teks dilanjutkan ke baris berikutnya
                    overflow = TextOverflow.Ellipsis, // Memotong teks dengan elipsis jika terlalu panjang
                    modifier = Modifier.weight(1f) // Mengatur lebar teks
                )
                IconButton(onClick = { onDeleteClick(produk) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Produk",
                        tint = Color(0xFFFF9900)
                    )
                }
                IconButton(onClick = { onUpdateClick(produk) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color(0xFFFF9900)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // Memberi jarak antara nama produk dan deskripsi
            Text(
                text = "Deskripsi:",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Gray
            )
            Text(
                text = produk.deskripsiProduk,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp)) // Memberi jarak antara deskripsi dan harga
            Text(
                text = "Harga:",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Gray
            )
            Text(
                text = produk.harga,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}