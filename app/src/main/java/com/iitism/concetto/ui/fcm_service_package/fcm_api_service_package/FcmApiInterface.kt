package com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmApiInterface {
    @Headers(
        "Content-Type:application/json",
        "Authorization:Key=AAAArz7ufTo:APA91bEAAeON-uGBtA2rXsrzyLoOjpAhSUeeEUKIlGnJhKI7vzjWdVjvhJ3a_AtBFafzKgHvUh6xde4jaRuiXI4jhKR-QvKlCFbtXKlpu3XuKtLU6k4p5UElqYjGJTVQN2gXCMhoX3lT"
    )
    @POST("send")
    fun sendFCMNotification(@Body fcmDataBody: FcmDataBody): Call<JsonObject>
}