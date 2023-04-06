package com.iitism.concetto.changePassword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iitism.concetto.retrofit.Resource
import kotlinx.coroutines.launch

class ChangeViewModel(
    val changePassRespo: ChangePassRespo
):ViewModel() {
    private val _changeResponse: MutableLiveData<Resource<ChangeResponse>> = MutableLiveData()
    val changeResponse: LiveData<Resource<ChangeResponse>>
        get() = _changeResponse
    fun changepassword(
        teamName:String,
        oldpassword:String,
        newpassword:String)=viewModelScope.launch {
        _changeResponse.value=changePassRespo?.changePassword(teamName,oldpassword,newpassword)
        Log.d("ChangePassword","ChangeViewModel->${_changeResponse}")
    }
}