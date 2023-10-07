package com.iitism.concetto.ui.noticeboardfragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.iitism.concetto.ui.noticeboardfragment.retrofit.AddNoticeDataBody
import com.iitism.concetto.ui.noticeboardfragment.retrofit.AddNoticeInterface
import com.iitism.concetto.ui.noticeboardfragment.retrofit.AddNoticeRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNoticeViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun addNoticeService(addNoticeBody:AddNoticeDataBody){
        val apiInterface=AddNoticeRetrofitInstance.getRetrofit()?.create(AddNoticeInterface::class.java)
        apiInterface?.addNotice(addNoticeBody)?.enqueue(object:Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d("Add Notice Response",response.body().toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("Add Notice Response","Failure")
            }
        })
    }
}