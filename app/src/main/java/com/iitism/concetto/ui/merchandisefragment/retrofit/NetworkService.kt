package com.iitism.concetto.ui.merchandisefragment.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {



    val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES) // Increase the connect timeout
        .readTimeout(5, TimeUnit.MINUTES)    // Increase the read timeout
        .writeTimeout(5, TimeUnit.MINUTES)   // Increase the write timeout
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://concetto-23-nuc1.onrender.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val merchandiseApiService: MerchandiseApiService = retrofit.create(MerchandiseApiService::class.java)
}