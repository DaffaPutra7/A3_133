package com.example.a3_133.ui.view.merk

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
import com.example.a3_133.model.Merk
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.merk.DetailMerkUiState
import com.example.a3_133.ui.viewmodel.merk.DetailMerkViewModel

object DestinasiDetailMerk : DestinasiNavigasi {
    override val route = "detailmerk"
    override val titleRes = "Detail Merk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMerkView(
    idMerk: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailMerkViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idMerk) {
        viewModel.getMerkById(idMerk)
    }

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Detail Merk",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        val uiState by viewModel.detailMerkUiState.collectAsState()

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp)) // Memberi jarak antara top app bar dan card detail
            BodyDetailMerk(
                detailMerkUiState = uiState,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BodyDetailMerk(
    detailMerkUiState: DetailMerkUiState,
    modifier: Modifier = Modifier
) {
    when (detailMerkUiState) {
        is DetailMerkUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailMerkUiState.Success -> {
            val merk = detailMerkUiState.merk
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Menghilangkan padding vertikal
            ) {
                ItemDetailMerk(merk = merk)
            }
        }
        is DetailMerkUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailMerkUiState.message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMerk(
    merk: Merk,
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
            ComponentDetailMerk(judul = "ID Merk", isinya = merk.idMerk)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMerk(judul = "Nama Merk", isinya = merk.namaMerk)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMerk(judul = "Deskripsi Merk", isinya = merk.deskripsiMerk)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailMerk(
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