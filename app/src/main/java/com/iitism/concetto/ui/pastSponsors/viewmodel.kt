package com.iitism.concetto.ui.pastSponsors

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class viewmodel: ViewModel() {

    private val _sponsorList = mutableStateListOf<sponsor>()

    val sponsorList : List<sponsor>
        get() = _sponsorList
    var error: String?=null

    fun getSponsorList(){

        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _userList.clear()
                _userList.addAll(apiService!!.getUsers())

            } catch (e:Exception){
                error = e.toString()
            }

        }
    }
}