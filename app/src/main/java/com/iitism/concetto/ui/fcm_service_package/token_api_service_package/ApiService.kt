package com.iitism.concetto.ui.fcm_service_package.token_api_service_package

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiService {
    fun addTokenService(token:String,context: Context){
        val dataBody=SendTokenDataBody(token)
        val apiInterface=RetrofitInstance.getRetrofit()?.create(ApiInterface::class.java)
        apiInterface?.addToken(dataBody)?.enqueue(object :Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                Toast.makeText(context,"Token is Registered for future notifications", Toast.LENGTH_LONG).show()
                Log.d("Token Gen","Success")
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                Toast.makeText(context,"Token Registration Error", Toast.LENGTH_LONG).show()
                Log.d("Token Gen","Failure")
            }

        })
    }
     suspend fun getRegisteredTokenListService(context: Context):ArrayList<String>{
        val list=ArrayList<String>()
        val apiInterface=RetrofitInstance.getRetrofit()?.create(ApiInterface::class.java)
         apiInterface?.getRegisteredTokenList()?.enqueue(object :Callback<JsonArray>{
             override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                 val jsonResponse=response.body()
                 if(jsonResponse!=null){
//                     Toast.makeText(context,jsonResponse.toString(), Toast.LENGTH_LONG).show()
                     Log.d("List Token","Success")
                     for (token in jsonResponse){
                         if(token!=null){
//                             Toast.makeText(context,token.toString().replace("\"",""), Toast.LENGTH_LONG).show()
                             list.add(token.toString().replace("\"",""))
//                             Toast.makeText(context,list.size.toString(), Toast.LENGTH_LONG).show()
                         }
                     }
                 }else{
                       Log.d("List Token","NULL")
//                     Toast.makeText(context,"Token List Size 0", Toast.LENGTH_LONG).show()
                 }

             }

             override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                 Log.d("No response","Failed to get data from api")
//                 Toast.makeText(context,"Token List Call Error", Toast.LENGTH_LONG).show()
             }

         })
        //Toast.makeText(context,"Return Size "+list.size.toString(), Toast.LENGTH_LONG).show()
        delay(2500)
         return list
     }

}