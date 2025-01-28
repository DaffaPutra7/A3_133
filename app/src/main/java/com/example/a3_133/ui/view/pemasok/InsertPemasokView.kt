package com.example.a3_133.ui.view.pemasok

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.pemasok.InsertPemasokUiEvent
import com.example.a3_133.ui.viewmodel.pemasok.InsertPemasokUiState
import com.example.a3_133.ui.viewmodel.pemasok.InsertPemasokViewModel
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.pemasok.PemasokErrorState
import kotlinx.coroutines.launch

object DestinasiEntryPemasok : DestinasiNavigasi {
    override val route = "pemasok_entry"
    override val titleRes = "Entry Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPemasokScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPemasokViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.pemasokuiState.snackBarMessage) {
        viewModel.pemasokuiState.snackBarMessage?.let { message ->
            val result = snackbarHostState.showSnackbar(message)
            if (result == SnackbarResult.ActionPerformed) {
                // Handle action
            }
            viewModel.resetSnackBarMessage()
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntryPemasok.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        EntryBodyPemasok(
            insertpemasokUiState = viewModel.pemasokuiState,
            onPemasokValueChange = viewModel::updateInsertPemasokState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPemasok()
                    if (viewModel.pemasokuiState.snackBarMessage == null) {
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
fun EntryBodyPemasok(
    insertpemasokUiState: InsertPemasokUiState,
    onPemasokValueChange: (InsertPemasokUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPemasok(
            insertpemasokUiEvent = insertpemasokUiState.insertPemasokUiEvent,
            isEntryValid = insertpemasokUiState.isEntryValid,
            onValueChange = onPemasokValueChange,
            modifier = Modifier.fillMaxWidth()
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
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPemasok(
    insertpemasokUiEvent: InsertPemasokUiEvent,
    isEntryValid: PemasokErrorState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPemasokUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertpemasokUiEvent.namaPemasok,
            onValueChange = { onValueChange(insertpemasokUiEvent.copy(namaPemasok = it)) },
            label = { Text("Nama Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.namaPemasok != null // Highlight error
        )
        if (isEntryValid.namaPemasok != null) {
            Text(
                text = isEntryValid.namaPemasok ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = insertpemasokUiEvent.alamatPemasok,
            onValueChange = { onValueChange(insertpemasokUiEvent.copy(alamatPemasok = it)) },
            label = { Text("Alamat Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.alamatPemasok != null // Highlight error
        )
        if (isEntryValid.alamatPemasok != null) {
            Text(
                text = isEntryValid.alamatPemasok ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = insertpemasokUiEvent.teleponPemasok,
            onValueChange = { onValueChange(insertpemasokUiEvent.copy(teleponPemasok = it)) },
            label = { Text("Telepon Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.teleponPemasok != null // Highlight error
        )
        if (isEntryValid.teleponPemasok != null) {
            Text(
                text = isEntryValid.teleponPemasok ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}