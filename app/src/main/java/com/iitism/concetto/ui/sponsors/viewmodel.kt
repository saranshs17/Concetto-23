package com.iitism.concetto.ui.sponsors

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStream

class viewmodel(private val context: Context): ViewModel() {

    private val _sponsorList = mutableStateListOf<Sponsor>()

    val sponsorList : List<Sponsor>
        get() = _sponsorList
    var error: String?=null

    fun getSponsorList(){

        viewModelScope.launch {
//            val apiService = APIService.getInstance()
            try {
                _sponsorList.clear()


                val inputStream : InputStream = context.assets.open("sponsorData.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()

                val json = String(buffer, Charsets.UTF_8)

                val gson = Gson()
                val sponsors = gson.fromJson(json, Array<Sponsor>::class.java)
                _sponsorList.addAll(sponsors)

            } catch (e:Exception){
                error = e.toString()
            }

        }
    }
}