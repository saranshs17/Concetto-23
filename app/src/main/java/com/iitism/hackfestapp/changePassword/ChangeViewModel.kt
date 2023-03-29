package com.iitism.hackfestapp.changePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iitism.hackfestapp.auth.LoginResponse
import com.iitism.hackfestapp.retrofit.Resource
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
    }
}