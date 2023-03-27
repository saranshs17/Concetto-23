package com.iitism.hackfestapp.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteData {
    companion object{
        private  const val BASE_URL="http://hackfest-backend-3y92.onrender.com/"
    }
    fun <Api> BuildApi(
        api:Class<Api>
    ):Api{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)

    }
}