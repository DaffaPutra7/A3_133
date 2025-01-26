package com.example.a3_133.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a3_133.A3Applications
import com.example.a3_133.ui.viewmodel.kategori.HomeKategoriViewModel
import com.example.a3_133.ui.viewmodel.kategori.InsertKategoriViewModel
import com.example.a3_133.ui.viewmodel.kategori.UpdateKategoriViewModel
import com.example.a3_133.ui.viewmodel.merk.HomeMerkViewModel
import com.example.a3_133.ui.viewmodel.merk.InsertMerkViewModel
import com.example.a3_133.ui.viewmodel.merk.UpdateMerkViewModel
import com.example.a3_133.ui.viewmodel.pemasok.HomePemasokViewModel
import com.example.a3_133.ui.viewmodel.pemasok.InsertPemasokViewModel
import com.example.a3_133.ui.viewmodel.pemasok.UpdatePemasokViewModel
import com.example.a3_133.ui.viewmodel.produk.DetailProdukViewModel
import com.example.a3_133.ui.viewmodel.produk.HomeProdukViewModel
import com.example.a3_133.ui.viewmodel.produk.InsertProdukViewModel
import com.example.a3_133.ui.viewmodel.produk.UpdateProdukViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeMerkViewModel(aplikasiA3().container.merkRepository) }
        initializer { InsertMerkViewModel(aplikasiA3().container.merkRepository) }
        initializer { UpdateMerkViewModel(aplikasiA3().container.merkRepository) }

        initializer { HomePemasokViewModel(aplikasiA3().container.pemasokRepository) }
        initializer { InsertPemasokViewModel(aplikasiA3().container.pemasokRepository) }
        initializer { UpdatePemasokViewModel(aplikasiA3().container.pemasokRepository) }

        initializer { HomeKategoriViewModel(aplikasiA3().container.kategoriRepository) }
        initializer { InsertKategoriViewModel(aplikasiA3().container.kategoriRepository) }
        initializer { UpdateKategoriViewModel(aplikasiA3().container.kategoriRepository) }

        initializer { HomeProdukViewModel(aplikasiA3().container.produkRepository) }
        initializer { InsertProdukViewModel(aplikasiA3().container.produkRepository,
            aplikasiA3().container.kategoriRepository, aplikasiA3().container.pemasokRepository, aplikasiA3().container.merkRepository) }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailProdukViewModel(savedStateHandle, aplikasiA3().container.produkRepository)
        }
        initializer { UpdateProdukViewModel(aplikasiA3().container.produkRepository,
            aplikasiA3().container.kategoriRepository, aplikasiA3().container.pemasokRepository, aplikasiA3().container.merkRepository) }
    }
}

fun CreationExtras.aplikasiA3(): A3Applications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as A3Applications)