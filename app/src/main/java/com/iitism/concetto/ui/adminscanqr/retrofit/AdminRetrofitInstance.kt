package com.iitism.concetto.ui.adminscanqr.retrofit

import com.iitism.concetto.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AdminRetrofitInstance() {


    private val Base_url = "https://hackfest-backend-3y92.onrender.com/"


    val api: AdminScanQrApi by lazy {
        Retrofit.Builder()
            .baseUrl(Base_url)
            .client(OkHttpClient.Builder().also { client->
                client.connectTimeout(30, TimeUnit.SECONDS)
                client.readTimeout(30,TimeUnit.SECONDS)
                if(BuildConfig.DEBUG){
                    val markattendance= HttpLoggingInterceptor();
                    markattendance.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(markattendance)
                }
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdminScanQrApi::class.java)
    }

}