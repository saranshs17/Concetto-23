package com.iitism.hackfestapp.changePassword

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PasswordChange {
    @FormUrlEncoded
    @POST("change_password")
    suspend fun changePass(
        @Field("Player_Email") teamName:String,
        @Field("old_password")oldPassword:String,
        @Field("new_password") newPassword:String

    ): ChangeResponse
}