package com.example.a3_133.ui.view.produk

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.a3_133.model.Produk
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.produk.DetailProdukUiState
import com.example.a3_133.ui.viewmodel.produk.DetailProdukViewModel

object DestinasiDetailProduk : DestinasiNavigasi {
    override val route = "detailproduk"
    override val titleRes = "Detail Produk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProdukView(
    idProduk: String,
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    navigateToKategori: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailProdukViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Fetch product details using the idProduk
    LaunchedEffect(idProduk, viewModel.updateTrigger) {
        viewModel.getProdukById(idProduk)
    }

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Detail Produk",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        val uiState by viewModel.detailProdukUiState.collectAsState()

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp)) // Memberi jarak antara top app bar dan card detail
            BodyDetailProduk(
                detailProdukUiState = uiState,
                modifier = Modifier.weight(1f)
            )

            val kategori = if (uiState is DetailProdukUiState.Success) {
                (uiState as DetailProdukUiState.Success).produk.kategori
            } else {
                ""
            }

            if (uiState is DetailProdukUiState.Success) {
                Spacer(modifier = Modifier.height(4.dp)) // Memberi jarak antara card detail dan tombol
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExtendedFloatingActionButton(
                        onClick = { navigateToKategori(kategori) },
                        shape = MaterialTheme.shapes.medium,
                        containerColor = Color(0xFFFF9900),
                        contentColor = Color.White,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Lihat Kategori")
                        Text(text = " Lihat Kategori")
                    }
                    ExtendedFloatingActionButton(
                        onClick = {
                            navigateToEdit((uiState as DetailProdukUiState.Success).produk.idProduk)
                            viewModel.updateData() // Panggil fungsi updateData setelah update data selesai
                        },
                        shape = MaterialTheme.shapes.medium,
                        containerColor = Color(0xFFFF9900),
                        contentColor = Color.White,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Produk")
                        Text(text = " Edit Produk")
                    }
                }
            }
        }
    }
}

@Composable
fun BodyDetailProduk(
    detailProdukUiState: DetailProdukUiState,
    modifier: Modifier = Modifier
) {
    when (detailProdukUiState) {
        is DetailProdukUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailProdukUiState.Success -> {
            val produk = detailProdukUiState.produk
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
            ) {
                ItemDetailProduk(produk = produk)
            }
        }
        is DetailProdukUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailProdukUiState.message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailProduk(
    produk: Produk,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailProduk(judul = "ID Produk", isinya = produk.idProduk)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProduk(judul = "Nama Produk", isinya = produk.namaProduk)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProduk(judul = "Deskripsi Produk", isinya = produk.deskripsiProduk)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProduk(judul = "Harga", isinya = produk.harga)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProduk(judul = "Stok", isinya = produk.stok)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProduk(judul = "Kategori", isinya = produk.kategori)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProduk(judul = "Pemasok", isinya = produk.pemasok)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProduk(judul = "Merk", isinya = produk.merk)
        }
    }
}

@Composable
fun ComponentDetailProduk(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}