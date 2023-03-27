package com.example.hackfestapp.ui.aboutus

import com.example.hackfestapp.ui.aboutus.retrofit.AboutUsApi
import com.example.hackfestapp.ui.aboutus.retrofit.RetrofitInstance
import retrofit2.Retrofit

class AboutUsRepository constructor(
    private val api: AboutUsApi
) {
    suspend fun getAllOrganizers() = api.getAllOrganizers()
}