package com.example.a3_133.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kategori(
    @SerialName("id_kategori")
    val idKategori: String,

    @SerialName("nama_kategori")
    val namaKategori: String,

    @SerialName("deskripsi_kategori")
    val deskripsiKategori: String
)
