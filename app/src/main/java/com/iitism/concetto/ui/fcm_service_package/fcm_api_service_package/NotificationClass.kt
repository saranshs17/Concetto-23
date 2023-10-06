package com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package

import com.google.gson.annotations.SerializedName

data class NotificationClass(
    @SerializedName("body")
    val body:String,
    @SerializedName("title")
    val title:String
)
