package com.iitism.concetto.ui.aboutus.retrofit

import com.iitism.concetto.ui.aboutus.AboutUsModel
import com.iitism.concetto.ui.noticeboardfragment.NoticeBoardModel
import retrofit2.Response
import retrofit2.http.GET

interface AboutUsApi {

    @GET("organizing")
    suspend fun getAllOrganizers(): Response<List<AboutUsModel>>

    @GET("announcement")
    suspend fun getAllNotices(): Response<List<NoticeBoardModel>>
}