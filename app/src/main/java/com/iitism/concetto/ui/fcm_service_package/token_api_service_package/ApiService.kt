package com.iitism.concetto.ui.fcm_service_package.token_api_service_package

import android.content.Context
import android.widget.Toast
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiService {
    fun addTokenService(token:String,context: Context){
        val dataBody=SendTokenDataBody(token)
        val apiInterface=RetrofitInstance.getRetrofit()?.create(ApiInterface::class.java)
        apiInterface?.addToken(dataBody)?.enqueue(object :Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Toast.makeText(context,"Token is Registered for future notifications", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context,"Token Registeration Error", Toast.LENGTH_LONG).show()
            }

        })
    }
}