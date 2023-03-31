package com.iitism.hackfestapp.changePassword

import android.util.Log
import com.iitism.hackfestapp.auth.Refractor.BaseRepository

class ChangePassRespo(private val passwordChange:PasswordChange) :BaseRepository(){
    suspend fun changePassword(
        teamName:String,
        oldpassword:String,
        newPassword:String
    ) = safeApiCall {
        Log.d("ChangePassworRepo","NewPassw->${newPassword},OldPassword->${oldpassword}")
        passwordChange.changePass(teamName,oldpassword,newPassword)
    }
}