package com.iitism.concetto.ui.allevents

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import retrofit2.Response

class AllEventsViewModel : ViewModel() {
    var EventsList = MutableLiveData<List<AllEventsDataModel>?>()
    private val retrofit: RetrofitInstanceEvents = RetrofitInstanceEvents() // Initialize Retrofit here

    suspend fun getAllEvents() {
        Log.d("Call", "Function Called")
        try {
            val response: Response<List<AllEventsDataModel>> = retrofit.allEventService.getAllEvents()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    // Data fetched successfully
                    Log.d("Data", data.toString())
                    EventsList.postValue(data)
                }
            } else {
                // Handle API error here
                // For example, you can throw a custom exception or return an empty list
                Log.e("API Error", "Response code: ${response.code()}")
            }
        } catch (e: Exception) {
            // Handle network error or other exceptions here
            // For example, you can throw a custom exception or log the error
            Log.e("Error", e.toString())
        }
    }
}
