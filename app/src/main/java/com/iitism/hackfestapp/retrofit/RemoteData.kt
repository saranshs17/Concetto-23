package com.iitism.hackfestapp.retrofit

import com.iitism.hackfestapp.BuildConfig
import android.text.format.Time
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteData {
    companion object{
        private  const val BASE_URL="http://hackfest-backend-3y92.onrender.com/"
    }
    fun <Api> BuildApi(
        api:Class<Api>
    ):Api{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client->
                client.connectTimeout(30, TimeUnit.SECONDS)
                client.readTimeout(30,TimeUnit.SECONDS)
                if(BuildConfig.DEBUG){
                    val loggin= HttpLoggingInterceptor();
                    loggin.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(loggin)
                }
            }.build()).addConverterFactory(GsonConverterFactory.create()).build().create(api)
    }
}
