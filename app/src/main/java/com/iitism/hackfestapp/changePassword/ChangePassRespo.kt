package com.iitism.hackfestapp.changePassword

import com.iitism.hackfestapp.auth.Refractor.BaseRepository

class ChangePassRespo(val passwordChange:PasswordChange) :BaseRepository(){
    suspend fun changePassword(
        teamName:String,
        oldpassword:String,
        newPassword:String
    ) = safeApiCall {
        passwordChange.changePass(teamName,oldpassword,newPassword)

    }
}