package com.example.a3_133.ui.view.pemasok

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
import com.example.a3_133.model.Pemasok
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.pemasok.DetailPemasokUiState
import com.example.a3_133.ui.viewmodel.pemasok.DetailPemasokViewModel

object DestinasiDetailPemasok : DestinasiNavigasi {
    override val route = "detailpemasok"
    override val titleRes = "Detail Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemasokView(
    idPemasok: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPemasokViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idPemasok) {
        viewModel.getPemasokById(idPemasok)
    }

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Detail Pemasok",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        val uiState by viewModel.detailPemasokUiState.collectAsState()

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp)) // Memberi jarak antara top app bar dan card detail
            BodyDetailPemasok(
                detailPemasokUiState = uiState,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BodyDetailPemasok(
    detailPemasokUiState: DetailPemasokUiState,
    modifier: Modifier = Modifier
) {
    when (detailPemasokUiState) {
        is DetailPemasokUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailPemasokUiState.Success -> {
            val pemasok = detailPemasokUiState.pemasok
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
            ) {
                ItemDetailPemasok(pemasok = pemasok)
            }
        }
        is DetailPemasokUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailPemasokUiState.message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailPemasok(
    pemasok: Pemasok,
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
            ComponentDetailPemasok(judul = "ID Pemasok", isinya = pemasok.idPemasok)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPemasok(judul = "Nama Pemasok", isinya = pemasok.namaPemasok)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPemasok(judul = "Alamat Pemasok", isinya = pemasok.alamatPemasok)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPemasok(judul = "No.Telepon Pemasok", isinya = pemasok.teleponPemasok)
        }
    }
}

@Composable
fun ComponentDetailPemasok(
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