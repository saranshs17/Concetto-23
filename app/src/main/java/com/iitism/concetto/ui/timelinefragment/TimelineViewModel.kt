package com.iitism.concetto.ui.timelinefragment

import android.content.Context
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

                val timeline = gson.fromJson(json,Array<timilineDataModel>::class.java)
                _timelineList.addAll(timeline)
            }
            catch (e: Exception){
                error= e.toString()
            }

        }
    }
}