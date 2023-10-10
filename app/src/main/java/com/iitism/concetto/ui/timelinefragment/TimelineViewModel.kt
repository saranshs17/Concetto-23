package com.iitism.concetto.ui.timelinefragment

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStream

class TimelineViewModel(private val context : Context) : ViewModel() {

    private val _timelineList = mutableStateListOf<timilineDataModel>()

    val timelineList : List<timilineDataModel>
        get()= _timelineList

    var error: String? = null

    lateinit var timeline: Array<timilineDataModel>

    fun getTimelineList(){
        viewModelScope.launch {
            try{
                _timelineList.clear()
                val inputStream: InputStream = context.assets.open("timeline.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()

                val json = String(buffer, Charsets.UTF_8)
                val gson = Gson()

                timeline = gson.fromJson(json,Array<timilineDataModel>::class.java)

//                val filteredData = data.filter { it.type == 1 }
//                Log.d("Data", data.toString())
//                ClubEventList.postValue(filteredData)
                _timelineList.addAll(timeline)


            }
            catch (e: Exception){
                error= e.toString()
            }

        }
    }

    fun filterDataByDate(selectedDate: String) {
        var filteredList =timeline.filter { it.date == selectedDate }
        Log.i("data1",_timelineList.size.toString())
        _timelineList.clear()

        _timelineList.addAll(filteredList)
        Log.i("data",filteredList.toString())
    }
}