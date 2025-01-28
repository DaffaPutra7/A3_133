package com.example.a3_133.ui.view.merk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.merk.InsertMerkViewModel
import com.example.a3_133.ui.viewmodel.merk.InsertUiEvent
import com.example.a3_133.ui.viewmodel.merk.InsertUiState
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.merk.MerkErrorState
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "merk_entry"
    override val titleRes = "Entry Merk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMerkScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMerkViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyMerk(
            insertUiState = viewModel.uiState,
            onMerkValueChange = viewModel::updateInsertMerkState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMerk()
                    navigateBack()
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
fun EntryBodyMerk(
    insertUiState: InsertUiState,
    onMerkValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputMerk(
            insertUiEvent = insertUiState.insertUiEvent,
            isEntryValid = insertUiState.isEntryValid,
            onValueChange = onMerkValueChange,
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
fun FormInputMerk(
    insertUiEvent: InsertUiEvent,
    isEntryValid: MerkErrorState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaMerk,
            onValueChange = { onValueChange(insertUiEvent.copy(namaMerk = it)) },
            label = { Text("Nama Merk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.namaMerk != null // Highlight error
        )
        if (isEntryValid.namaMerk != null) {
            Text(
                text = isEntryValid.namaMerk ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = insertUiEvent.deskripsiMerk,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiMerk = it)) },
            label = { Text("Deskripsi Merk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.deskripsiMerk != null // Highlight error
        )
        if (isEntryValid.deskripsiMerk != null) {
            Text(
                text = isEntryValid.deskripsiMerk ?: "",
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