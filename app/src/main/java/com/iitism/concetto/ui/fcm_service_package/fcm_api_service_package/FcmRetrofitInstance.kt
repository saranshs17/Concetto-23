package com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FcmRetrofitInstance {
    private var retrofit: Retrofit?=null
    val baseUrl="https://fcm.googleapis.com/fcm/"
    fun getRetrofit(): Retrofit?{
        if (retrofit==null){
            retrofit= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).build()
        }
        return retrofit
    }
}