package com.iitism.concetto.ui.clubevents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.transform.Transformation
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import retrofit2.Response

class ClubEventsViewModel : ViewModel() {
    var ClubEventList = MutableLiveData<List<Club_dataclass>?>()
   // var FilteredClubEvent
    private val retrofit: RetrofitInstanceClubEvents = RetrofitInstanceClubEvents() // Initialize Retrofit here

    suspend fun getClubEvents() {
        Log.d("Call", "Function Called")
        try {
            val response: Response<List<Club_dataclass>> = retrofit.clubEventService.getAllEvents()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null  ) {
                    // Data fetched successfully
                    val filteredData = data.filter { it.type == 1 }
                    Log.d("Data", data.toString())
                    ClubEventList.postValue(filteredData)
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

