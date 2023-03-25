package com.example.hackfestapp.auth.Refractor

import com.example.hackfestapp.retrofit.AuthApiLogin

class LoginRepository(private  val apiLogin: AuthApiLogin): BaseRepository() {
    suspend fun login(
         teamName:String,
        password:String,
    ) = safeApiCall {
        apiLogin.login(teamName,password)
    }


}