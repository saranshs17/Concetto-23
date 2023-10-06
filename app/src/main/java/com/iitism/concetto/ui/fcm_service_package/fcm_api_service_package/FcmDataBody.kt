package com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package

import com.google.gson.annotations.SerializedName

data class FcmDataBody(
    @SerializedName("registration_ids")
    val registration_ids:ArrayList<String>,
    @SerializedName("notification")
    val notification:NotificationClass
)
