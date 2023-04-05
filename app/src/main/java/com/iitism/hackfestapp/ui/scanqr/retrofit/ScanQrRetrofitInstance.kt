package com.iitism.hackfestapp.ui.scanqr.retrofit

import com.iitism.hackfestapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ScanQrRetrofitInstance(var Base_url : String?) {


    val api: ScanQrApi by lazy {
        Retrofit.Builder()
            .baseUrl(Base_url)
            .client(OkHttpClient.Builder().also { client ->
                client.connectTimeout(30, TimeUnit.SECONDS)
                client.readTimeout(30, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(interceptor)
                }
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScanQrApi::class.java)
    }



}