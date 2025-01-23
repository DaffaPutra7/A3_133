package com.example.a3_133.service

import com.example.a3_133.model.Kategori
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KategoriService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("getkategori.php")
    suspend fun getKategori(): List<Kategori>

    @GET("get1kategori.php")
    suspend fun getKategoriById(@Query("id_kategori") id_kategori:String): Kategori

    @POST("insertkategori.php")
    suspend fun insertKategori(@Body kategori: Kategori)

    @PUT("editkategori.php")
    suspend fun updateKategori(@Query("id_kategori") id_kategori: String, @Body kategori: Kategori)

    @DELETE("deletekategori.php")
    suspend fun deleteKategori(@Query("id_kategori") id_kategori: String) : retrofit2.Response<Void>
}