package com.iitism.concetto.ui.clubevents

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.transform.Transformation
import com.iitism.concetto.MainActivity
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import retrofit2.Response

class ClubEventsViewModel(application : Application) : AndroidViewModel(application) {
    var ClubEventList = MutableLiveData<List<Club_dataclass>?>()
   // var FilteredClubEvent
    private val retrofit: RetrofitInstanceClubEvents = RetrofitInstanceClubEvents() // Initialize Retrofit here
    private val context = getApplication<Application>().applicationContext
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
            val intent = Intent(context, MainActivity:: class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ContextCompat.startActivities(context, arrayOf(intent))
        }

    }


}

