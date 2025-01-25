package com.example.a3_133.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Produk(
    @SerialName("id_produk")
    val idProduk: String,

    @SerialName("nama_produk")
    val namaProduk: String,

    @SerialName("deskripsi_produk")
    val deskripsiProduk: String,

    @SerialName("harga")
    val harga: String,

    @SerialName("stok")
    val stok: String,

    @SerialName("kategori")
    val kategori: String,

    @SerialName("pemasok")
    val pemasok: String,

    @SerialName("merk")
    val merk: String
)
