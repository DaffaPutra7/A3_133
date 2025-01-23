package com.example.a3_133.repository

import com.example.a3_133.model.Produk
import com.example.a3_133.service.ProdukService
import okio.IOException

interface ProdukRepository {
    suspend fun getProduk(): List<Produk>
    suspend fun getProdukById(idproduk : String) : Produk
    suspend fun insertProduk(produk: Produk)
    suspend fun updateProduk(idproduk: String, produk: Produk)
    suspend fun deleteProduk(idproduk: String)
}

class NetworkProdukRepository( private val produkApiService: ProdukService) : ProdukRepository {
    override suspend fun insertProduk(produk: Produk) {
        produkApiService.insertProduk(produk)
    }

    override suspend fun updateProduk(idproduk: String, produk: Produk) {
        produkApiService.updateProduk(idproduk, produk)
    }

    override suspend fun deleteProduk(idproduk: String) {
        try {
            val response = produkApiService.deleteProduk(idproduk)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete produk. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProduk(): List<Produk> = produkApiService.getProduk()

    override suspend fun getProdukById(idproduk: String): Produk {
        return produkApiService.getProdukById(idproduk)
    }
}