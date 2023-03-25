package com.example.hackfestapp.auth.Refractor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackfestapp.auth.LoginResponse

import com.example.hackfestapp.retrofit.Resource
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