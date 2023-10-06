package com.iitism.concetto.ui.fcm_service_package.token_api_service_package

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers(
        "Content-Type:application/json"
    )
    @POST("addToken")
    fun addToken(@Body body:SendTokenDataBody): Call<JsonObject>
    @Headers(
        "Content-Type:application/json"
    )
    @GET("tokens")
    fun getRegisteredTokenList(): Call<JsonObject>
}