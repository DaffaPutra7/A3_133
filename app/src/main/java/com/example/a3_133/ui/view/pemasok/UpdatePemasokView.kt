package com.example.a3_133.ui.view.pemasok

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.pemasok.UpdatePemasokUiEvent
import com.example.a3_133.ui.viewmodel.pemasok.UpdatePemasokUiState
import com.example.a3_133.ui.viewmodel.pemasok.UpdatePemasokViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePemasokView(
    navigateBack: () -> Unit,
    id: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePemasokViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uipemasokState = viewModel.pemasokuiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id) {
        viewModel.getPemasokById(id)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = "Update Pemasok",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBodyPemasok(
            updatepemasokUiState = uipemasokState,
            onPemasokValueChange = viewModel::updatePemasokState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePemasok()
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
fun UpdateBodyPemasok(
    updatepemasokUiState: UpdatePemasokUiState,
    onPemasokValueChange: (UpdatePemasokUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormUpdatePemasok(
            updatepemasokUiEvent = updatepemasokUiState.updatePemasokUiEvent,
            onValueChange = onPemasokValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormUpdatePemasok(
    updatepemasokUiEvent: UpdatePemasokUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePemasokUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updatepemasokUiEvent.namaPemasok,
            onValueChange = { onValueChange(updatepemasokUiEvent.copy(namaPemasok = it)) },
            label = { Text("Nama Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = updatepemasokUiEvent.alamatPemasok,
            onValueChange = { onValueChange(updatepemasokUiEvent.copy(alamatPemasok = it)) },
            label = { Text("Alamat Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = updatepemasokUiEvent.teleponPemasok,
            onValueChange = { onValueChange(updatepemasokUiEvent.copy(teleponPemasok = it)) },
            label = { Text("Telepon Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            enabled = enabled,
            singleLine = true
        )
    }
}