package com.example.hackfestapp.ui.aboutus.retrofit

import com.example.hackfestapp.ui.aboutus.AboutUsModel
import retrofit2.Response
import retrofit2.http.GET

interface AboutUsApi {

    @GET("organizing")
    suspend fun getAllOrganizers() : Response<List<AboutUsModel>>

}