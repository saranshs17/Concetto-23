package com.iitism.concetto.ui.noticeboardfragment.retrofit

import com.google.gson.annotations.SerializedName

data class AddNoticeDataBody(
    @SerializedName("title")
    val title:String,
    @SerializedName("message")
    val message:String
)
