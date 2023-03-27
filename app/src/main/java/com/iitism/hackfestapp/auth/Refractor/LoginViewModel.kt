package com.iitism.hackfestapp.auth.Refractor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iitism.hackfestapp.auth.LoginResponse

import com.iitism.hackfestapp.retrofit.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginrepository: LoginRepository
): ViewModel() {
    private val _loginResponse:MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
    get() = _loginResponse
    fun login(
        teamName:String,
        password:String)=viewModelScope.launch {
        _loginResponse.value=loginrepository?.login(teamName,password)
    }

}