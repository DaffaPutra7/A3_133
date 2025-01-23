package com.example.a3_133.service

import com.example.a3_133.model.Produk
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProdukService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("getproduk.php")
    suspend fun getProduk(): List<Produk>

    @GET("get1produk.php")
    suspend fun getProdukById(@Query("id_produk") id_produk:String): Produk

    @POST("insertproduk.php")
    suspend fun insertProduk(@Body produk: Produk)

    @PUT("editproduk.php")
    suspend fun updateProduk(@Query("id_produk") id_produk: String, @Body produk: Produk)

    @DELETE("deleteproduk.php")
    suspend fun deleteProduk(@Query("id_produk") id_produk: String) : retrofit2.Response<Void>
}