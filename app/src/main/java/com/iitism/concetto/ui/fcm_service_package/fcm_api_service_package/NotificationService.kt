package com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.JsonObject
import com.iitism.concetto.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class NotificationService {
    fun sendNotification(tokenList:ArrayList<String>, body: String, title:String){
        val notificationClass=NotificationClass(body,title)
        val fcmDataBody=FcmDataBody(tokenList,notificationClass)
        val apiInterface= FcmRetrofitInstance.getRetrofit()?.create(FcmApiInterface::class.java)
        apiInterface?.sendFCMNotification(fcmDataBody)?.enqueue(object :Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d("Notification to me",response.body().toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("Notification","Error notification")
            }

        })
    }
}