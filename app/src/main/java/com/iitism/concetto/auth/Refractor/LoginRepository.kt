package com.iitism.concetto.auth.Refractor

import com.iitism.concetto.retrofit.AuthApiLogin

class LoginRepository(private  val apiLogin: AuthApiLogin): BaseRepository() {
    suspend fun login(
         teamName:String,
        password:String,
    ) = safeApiCall {
        apiLogin.login(teamName,password)
    }


}