package com.iitism.concetto.ui.registrationEvent.retrofit

import android.location.Criteria
import com.iitism.concetto.ui.merchandisefragment.retrofit.ApiResponse
import com.iitism.concetto.ui.singleevent.SingleEventModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getCriteria(
        @Url endpoint : String
    ): Response<ApiResponseForCriteria>

    @POST("api/eventRegitration")
    fun uplaodData(@Body RegData: RegisterModel) : Call<ApiResponse>


}