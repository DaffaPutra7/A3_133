package com.example.a3_133.ui.view.produk

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3_133.ui.customwidget.CustomeTopAppBar
import com.example.a3_133.ui.viewmodel.PenyediaViewModel
import com.example.a3_133.ui.viewmodel.produk.UpdateProdukUiEvent
import com.example.a3_133.ui.viewmodel.produk.UpdateProdukUiState
import com.example.a3_133.ui.viewmodel.produk.UpdateProdukViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProdukView(
    navigateBack: () -> Unit,
    idProduk: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateProdukViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.produkuiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val kategoriList by viewModel::kategoriList
    val pemasokList by viewModel::pemasokList
    val merkList by viewModel::merkList

    LaunchedEffect(idProduk) {
        viewModel.getProdukById(idProduk)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = "Update Produk",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = uiState,
            onProdukValueChange = viewModel::updateProdukState,
            kategoriList = kategoriList,
            pemasokList = pemasokList,
            merkList = merkList,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateProduk()
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
fun UpdateBody(
    updateUiState: UpdateProdukUiState,
    onProdukValueChange: (UpdateProdukUiEvent) -> Unit,
    kategoriList: List<String>,
    pemasokList: List<String>,
    merkList: List<String>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp) // Menambahkan padding yang lebih besar untuk ruang putih yang lebih baik
    ) {
        FormInputProduk(
            updateProdukUiEvent = updateUiState.updateProdukUiEvent,
            onValueChange = onProdukValueChange,
            kategoriList = kategoriList,
            pemasokList = pemasokList,
            merkList = merkList,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.medium, // Bentuk tombol yang lebih baik
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9900), // Warna tombol oranye sesuai tema
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
fun FormInputProduk(
    updateProdukUiEvent: UpdateProdukUiEvent,
    kategoriList: List<String>,
    pemasokList: List<String>,
    merkList: List<String>,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateProdukUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Menambah jarak antar input untuk keterbacaan yang lebih baik
    ) {
        OutlinedTextField(
            value = updateProdukUiEvent.namaProduk,
            onValueChange = { onValueChange(updateProdukUiEvent.copy(namaProduk = it)) },
            label = { Text("Nama Produk") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF9900),
                focusedLabelColor = Color(0xFFFF9900)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateProdukUiEvent.deskripsiProduk,
            onValueChange = { onValueChange(updateProdukUiEvent.copy(deskripsiProduk = it)) },
            label = { Text("Deskripsi Produk") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF9900),
                focusedLabelColor = Color(0xFFFF9900)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateProdukUiEvent.harga,
            onValueChange = { onValueChange(updateProdukUiEvent.copy(harga = it)) },
            label = { Text("Harga") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF9900),
                focusedLabelColor = Color(0xFFFF9900)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = updateProdukUiEvent.stok,
            onValueChange = { onValueChange(updateProdukUiEvent.copy(stok = it)) },
            label = { Text("Stok") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF9900),
                focusedLabelColor = Color(0xFFFF9900)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        DropdownSelectorUpdate(
            label = "Kategori",
            items = kategoriList,
            selectedItem = updateProdukUiEvent.kategori,
            onItemSelected = { onValueChange(updateProdukUiEvent.copy(kategori = it)) }
        )
        DropdownSelectorUpdate(
            label = "Pemasok",
            items = pemasokList,
            selectedItem = updateProdukUiEvent.pemasok,
            onItemSelected = { onValueChange(updateProdukUiEvent.copy(pemasok = it)) }
        )
        DropdownSelectorUpdate(
            label = "Merk",
            items = merkList,
            selectedItem = updateProdukUiEvent.merk,
            onItemSelected = { onValueChange(updateProdukUiEvent.copy(merk = it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelectorUpdate(
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF9900),
                focusedLabelColor = Color(0xFFFF9900)
            ),
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