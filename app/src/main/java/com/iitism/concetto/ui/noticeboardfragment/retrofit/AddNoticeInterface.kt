package com.iitism.concetto.ui.noticeboardfragment.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddNoticeInterface {
    @Headers(
        "Content-Type:application/json"
    )
    @POST("addNotice")
    fun addNotice(@Body body:AddNoticeDataBody): Call<JsonObject>
}