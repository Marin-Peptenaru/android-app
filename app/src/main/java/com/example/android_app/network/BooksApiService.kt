package com.example.android_app.network

import com.example.android_app.data.AuthDTO
import com.example.android_app.data.Book
import com.example.android_app.data.BookDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val baseUrl = "http://10.0.2.2:5000"


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(baseUrl).build();

interface BooksApiService {
    @GET("/api/books")
    fun getBooks(@Header("Authorization") token: String): Call<List<BookDTO>>

    @GET("/api/books/{isbn}")
    fun getBook(@Header("Authorization") token: String, @Path("isbn") isbn: String): Call<BookDTO>

    @POST("/api/books")
    fun addBook(@Header("Authorization") token: String, @Body b: BookDTO): Call<BookDTO>

    @PUT("/api/books")
    fun updateBook(@Header("Authorization") token: String, @Body b: BookDTO): Call<BookDTO>
}

interface BooksAuthService {
    @POST("/auth")
    fun authenticate(@Body authenticationData: AuthDTO): Call<String>
}


object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}

object BooksAuth {
    val retrofitService: BooksAuthService by lazy {
        retrofit.create(BooksAuthService::class.java)
    }
}
