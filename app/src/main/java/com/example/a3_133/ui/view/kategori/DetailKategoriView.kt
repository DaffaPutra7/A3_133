package com.example.a3_133.ui.view.kategori

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.model.Kategori
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.kategori.DetailKategoriUiState
import com.example.a3_133.ui.viewmodel.kategori.DetailKategoriViewModel

object DestinasiDetailKategori : DestinasiNavigasi {
    override val route = "detailkategori"
    override val titleRes = "Detail Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKategoriView(
    idKategori: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idKategori) {
        viewModel.getKategoriById(idKategori)
    }

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Detail Kategori",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        val uiState by viewModel.detailKategoriUiState.collectAsState()

        Column(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp)) // Memberi jarak antara top app bar dan card detail
            BodyDetailKategori(
                detailKategoriUiState = uiState,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BodyDetailKategori(
    detailKategoriUiState: DetailKategoriUiState,
    modifier: Modifier = Modifier
) {
    when (detailKategoriUiState) {
        is DetailKategoriUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailKategoriUiState.Success -> {
            val kategori = detailKategoriUiState.kategori
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
            ) {
                ItemDetailKategori(kategori = kategori)
            }
        }
        is DetailKategoriUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailKategoriUiState.message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailKategori(
    kategori: Kategori,
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
            ComponentDetailKategori(judul = "ID Kategori", isinya = kategori.idKategori)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailKategori(judul = "Nama Kategori", isinya = kategori.namaKategori)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailKategori(judul = "Deskripsi Kategori", isinya = kategori.deskripsiKategori)
        }
    }
}

@Composable
fun ComponentDetailKategori(
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