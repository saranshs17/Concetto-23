package com.iitism.concetto.ui.fcm_service_package.token_api_service_package

import com.google.gson.annotations.SerializedName

data class SendTokenDataBody(
    @SerializedName("token")
    val token:String
)
