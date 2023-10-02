package com.iitism.concetto.ui.coreteam

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStream

class CoreTeamViewModel(private  val context : Context): ViewModel() {

    private  val _coreTeamList = mutableStateListOf<CoreTeamDataModel>()

    val coreTeamList : List<CoreTeamDataModel>
        get() = _coreTeamList

    var error: String? = null

    fun getCoreTeamList()
    {
         viewModelScope.launch {

             try {
                 _coreTeamList.clear()
                 val inputStream: InputStream = context.assets.open("teams.json")
                 val size = inputStream.available()
                 val buffer = ByteArray(size)
                 inputStream.read(buffer)
                 inputStream.close()

                 val json = String(buffer, Charsets.UTF_8)
                 val gson = Gson()

                 val coreTeam = gson.fromJson(json, Array<CoreTeamDataModel>::class.java)
                 _coreTeamList.addAll(coreTeam)
             }
             catch (e: Exception)
             {
                 error = e.toString()
             }

         }
    }

}