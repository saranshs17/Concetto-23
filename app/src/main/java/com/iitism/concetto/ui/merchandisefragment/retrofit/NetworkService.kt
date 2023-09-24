package com.iitism.concetto.ui.merchandisefragment.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://concetto-23-nuc1.onrender.com/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val merchandiseApiService: MerchandiseApiService = retrofit.create(MerchandiseApiService::class.java)
}