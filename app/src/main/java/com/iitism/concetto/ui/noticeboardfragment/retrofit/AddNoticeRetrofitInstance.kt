package com.iitism.concetto.ui.noticeboardfragment.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AddNoticeRetrofitInstance {
    private var retrofit: Retrofit?=null
    val baseUrl="https://concetto-backend-clone.onrender.com/api/"
    fun getRetrofit(): Retrofit?{
        if (retrofit==null){
            retrofit= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).build()
        }
        return retrofit
    }
}