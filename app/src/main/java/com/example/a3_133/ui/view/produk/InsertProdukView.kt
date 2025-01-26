package com.example.a3_133.ui.view.produk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.navigation.DestinasiNavigasi
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.produk.InsertProdukUiEvent
import com.example.a3_133.ui.viewmodel.produk.InsertProdukUiState
import com.example.a3_133.ui.viewmodel.produk.InsertProdukViewModel
import kotlinx.coroutines.launch

object DestinasiEntryProduk : DestinasiNavigasi {
    override val route = "produk_entry"
    override val titleRes = "Entry Produk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryProdukScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertProdukViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.produkuiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val kategoriList by viewModel::kategoriList
    val pemasokList by viewModel::pemasokList
    val merkList by viewModel::merkList

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntryProduk.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        EntryBodyProduk(
            insertProdukUiState = uiState,
            onProdukValueChange = { updateEvent ->
                viewModel.updateInsertProdukState(updateEvent)
            },
            kategoriList = kategoriList,
            pemasokList = pemasokList,
            merkList = merkList,
            onClick = {
                coroutineScope.launch {
                    viewModel.insertProduk()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp) // Menambahkan padding vertikal yang konsisten
        )
    }
}

@Composable
fun EntryBodyProduk(
    insertProdukUiState: InsertProdukUiState,
    onProdukValueChange: (InsertProdukUiEvent) -> Unit,
    kategoriList: List<String>,
    pemasokList: List<String>,
    merkList: List<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), // Mengatur jarak antar elemen
        modifier = modifier
    ) {
        FormInputProduk(
            insertProdukUiEvent = insertProdukUiState.insertProdukUiEvent,
            onValueChange = onProdukValueChange,
            kategoriList = kategoriList,
            pemasokList = pemasokList,
            merkList = merkList,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9900),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp) // Menambahkan padding vertikal untuk tombol
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputProduk(
    insertProdukUiEvent: InsertProdukUiEvent,
    kategoriList: List<String>,
    pemasokList: List<String>,
    merkList: List<String>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertProdukUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Mengatur jarak antar input
    ) {
        OutlinedTextField(
            value = insertProdukUiEvent.namaProduk,
            onValueChange = { onValueChange(insertProdukUiEvent.copy(namaProduk = it)) },
            label = { Text("Nama Produk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertProdukUiEvent.deskripsiProduk,
            onValueChange = { onValueChange(insertProdukUiEvent.copy(deskripsiProduk = it)) },
            label = { Text("Deskripsi Produk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertProdukUiEvent.harga,
            onValueChange = { onValueChange(insertProdukUiEvent.copy(harga = it)) },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = insertProdukUiEvent.stok,
            onValueChange = { onValueChange(insertProdukUiEvent.copy(stok = it)) },
            label = { Text("Stok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        DropdownSelector(
            label = "Kategori",
            items = kategoriList,
            selectedItem = insertProdukUiEvent.kategori,
            onItemSelected = { onValueChange(insertProdukUiEvent.copy(kategori = it)) }
        )
        DropdownSelector(
            label = "Pemasok",
            items = pemasokList,
            selectedItem = insertProdukUiEvent.pemasok,
            onItemSelected = { onValueChange(insertProdukUiEvent.copy(pemasok = it)) }
        )
        DropdownSelector(
            label = "Merk",
            items = merkList,
            selectedItem = insertProdukUiEvent.merk,
            onItemSelected = { onValueChange(insertProdukUiEvent.copy(merk = it)) }
        )
    }
}

@Composable
fun DropdownSelector(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}