package com.iitism.concetto.ui.fcm_service_package.token_api_service_package

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private var retrofit: Retrofit?=null
    val baseUrl="https://concetto-backend-clone.onrender.com/api/"
    fun getRetrofit(): Retrofit?{
        if (retrofit==null){
            retrofit=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).build()
        }
        return retrofit
    }
}