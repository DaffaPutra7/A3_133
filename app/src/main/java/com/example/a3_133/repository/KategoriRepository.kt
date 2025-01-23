package com.example.a3_133.repository

import com.example.a3_133.model.Kategori
import com.example.a3_133.service.KategoriService
import okio.IOException

interface KategoriRepository {
    suspend fun getKategori(): List<Kategori>
    suspend fun getKategoriById(idkategori : String) : Kategori
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(idkategori: String, kategori: Kategori)
    suspend fun deleteKategori(idkategori: String)
}

class NetworkKategoriRepository( private val kategoriApiService: KategoriService) : KategoriRepository {
    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun updateKategori(idkategori: String, kategori: Kategori) {
        kategoriApiService.updateKategori(idkategori, kategori)
    }

    override suspend fun deleteKategori(idkategori: String) {
        try {
            val response = kategoriApiService.deleteKategori(idkategori)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kategori. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKategori(): List<Kategori> = kategoriApiService.getKategori()

    override suspend fun getKategoriById(idkategori: String): Kategori {
        return kategoriApiService.getKategoriById(idkategori)
    }
}