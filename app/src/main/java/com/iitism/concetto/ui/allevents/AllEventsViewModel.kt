package com.iitism.concetto.ui.allevents

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iitism.concetto.MainActivity
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import com.iitism.concetto.ui.homefragment.HomeFragment
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class AllEventsViewModel (application : Application): AndroidViewModel(application) {
    var EventsList = MutableLiveData<List<AllEventsDataModel>?>()
    var DepartmentEventList = MutableLiveData<List<AllEventsDataModel>?>()
    private val retrofit: RetrofitInstanceEvents = RetrofitInstanceEvents() // Initialize Retrofit here
    private val context = getApplication<Application>().applicationContext
    suspend fun getAllEvents() {
        Log.d("Call", "Function Called")
        try {
            val response: Response<List<AllEventsDataModel>> = retrofit.allEventService.getAllEvents()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    // Data fetched successfully
//                    Log.d("Data", data.toString())
                    EventsList.postValue(data)

                    val filteredData = data.filter { it.type == 2 }
                    DepartmentEventList.postValue(filteredData)
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
            //Toast.makeText(context,"RETRY :)",Toast.LENGTH_LONG).show()
            val intent = Intent(context,MainActivity:: class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivities(context, arrayOf(intent))

        }
    }




}
