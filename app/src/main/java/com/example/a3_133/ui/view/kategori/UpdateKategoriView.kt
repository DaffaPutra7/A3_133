package com.example.a3_133.ui.view.kategori

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.kategori.UpdateKategoriErrorState
import com.example.a3_133.ui.viewmodel.kategori.UpdateKategoriUiEvent
import com.example.a3_133.ui.viewmodel.kategori.UpdateKategoriUiState
import com.example.a3_133.ui.viewmodel.kategori.UpdateKategoriViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKategoriView(
    navigateBack: () -> Unit,
    id: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uikategoriState = viewModel.kategoriuiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id) {
        viewModel.getKategoriById(id)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = "Update Kategori",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBodyKategori(
            updateKategoriUiState = uikategoriState,
            onKategoriValueChange = viewModel::updateKategoriState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKategori()
                    if (viewModel.kategoriuiState.isSuccess) {
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun UpdateBodyKategori(
    updateKategoriUiState: UpdateKategoriUiState,
    onKategoriValueChange: (UpdateKategoriUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormUpdateKategori(
            updateKategoriUiEvent = updateKategoriUiState.updateKategoriUiEvent,
            onValueChange = onKategoriValueChange,
            modifier = Modifier.fillMaxWidth(),
            errorState = updateKategoriUiState.isEntryValid
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9900),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormUpdateKategori(
    updateKategoriUiEvent: UpdateKategoriUiEvent,
    errorState: UpdateKategoriErrorState,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateKategoriUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateKategoriUiEvent.namaKategori,
            onValueChange = { onValueChange(updateKategoriUiEvent.copy(namaKategori = it)) },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = errorState.namaKategori != null,
            supportingText = { Text(errorState.namaKategori ?: "") }
        )

        OutlinedTextField(
            value = updateKategoriUiEvent.deskripsiKategori,
            onValueChange = { onValueChange(updateKategoriUiEvent.copy(deskripsiKategori = it)) },
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = errorState.deskripsiKategori != null,
            supportingText = { Text(errorState.deskripsiKategori ?: "") }
        )
    }
}