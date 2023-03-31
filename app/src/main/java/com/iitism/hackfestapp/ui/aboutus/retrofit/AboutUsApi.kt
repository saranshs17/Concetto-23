package com.iitism.hackfestapp.ui.aboutus.retrofit

import com.iitism.hackfestapp.retrofit.Resource
import com.iitism.hackfestapp.ui.aboutus.AboutUsModel
import com.iitism.hackfestapp.ui.noticeboardfragment.NoticeBoardModel
import retrofit2.Response
import retrofit2.http.GET

interface AboutUsApi {

    @GET("organizing")
    suspend fun getAllOrganizers() : Response<List<AboutUsModel>>

    @GET("announcement")
    suspend fun getAllNotices() : Response<List<NoticeBoardModel>>

}