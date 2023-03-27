package com.iitism.hackfestapp.auth.Refractor

import com.iitism.hackfestapp.retrofit.AuthApiLogin

class LoginRepository(private  val apiLogin: AuthApiLogin): BaseRepository() {
    suspend fun login(
         teamName:String,
        password:String,
    ) = safeApiCall {
        apiLogin.login(teamName,password)
    }


}