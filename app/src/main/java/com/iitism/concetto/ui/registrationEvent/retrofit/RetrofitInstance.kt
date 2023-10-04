package com.iitism.concetto.ui.registrationEvent.retrofit

import com.iitism.concetto.ui.merchandisefragment.retrofit.MerchandiseApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS) // Increase the connect timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Increase the read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Increase the write timeout
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://concetto-backend-heli.onrender.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
   public val apiService: ApiService = retrofit.create(ApiService::class.java)
}