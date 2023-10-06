package com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package

import android.util.Log
import com.google.gson.JsonObject
import com.iitism.concetto.ui.fcm_service_package.token_api_service_package.ApiInterface
import com.iitism.concetto.ui.fcm_service_package.token_api_service_package.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationService {
    fun sendNotification(tokenList:ArrayList<String>,body:String,title:String){
        val notificationClass=NotificationClass(body,title)
        val fcmDataBody=FcmDataBody(tokenList,notificationClass)
        val apiInterface= FcmRetrofitInstance.getRetrofit()?.create(FcmApiInterface::class.java)
        apiInterface?.sendFCMNotification(fcmDataBody)?.enqueue(object :Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d("Notification",response.body().toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("Notification","Error notification")
            }

        })
    }
}