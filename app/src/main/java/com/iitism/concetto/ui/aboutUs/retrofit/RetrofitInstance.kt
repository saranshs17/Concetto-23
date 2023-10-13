package com.iitism.concetto.ui.aboutUs.retrofit

//import androidx.databinding.ktx.BuildConfig
import com.iitism.concetto.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    val Base_url = "https://concetto-backend-clone.onrender.com/api/"

    val api: AboutUsApi by lazy {
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
            .create(AboutUsApi::class.java)
    }

}