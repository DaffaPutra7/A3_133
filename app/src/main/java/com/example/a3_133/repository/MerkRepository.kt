package com.example.a3_133.repository

import com.example.a3_133.model.Merk
import com.example.a3_133.service.MerkService
import okio.IOException

interface MerkRepository {
    suspend fun getMerk(): List<Merk>
    suspend fun getMerkById(idmerk : String) : Merk
    suspend fun insertMerk(merk: Merk)
    suspend fun updateMerk(idmerk: String, merk: Merk)
    suspend fun deleteMerk(idmerk: String)
}

class NetworkMerkRepository( private val merkApiService: MerkService) : MerkRepository {
    override suspend fun insertMerk(merk: Merk) {
        merkApiService.insertMerk(merk)
    }

    override suspend fun updateMerk(idmerk: String, merk: Merk) {
        merkApiService.updateMerk(idmerk, merk)
    }

    override suspend fun deleteMerk(idmerk: String) {
        try {
            val response = merkApiService.deleteMerk(idmerk)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete merk. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMerk(): List<Merk> = merkApiService.getMerk()

    override suspend fun getMerkById(idmerk: String): Merk {
        return merkApiService.getMerkById(idmerk)
    }
}