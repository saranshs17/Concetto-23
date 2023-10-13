package com.iitism.concetto.ui.fcm_service_package.fcm_api_service_package

import com.google.gson.annotations.SerializedName

data class NotificationClass(
    @SerializedName("body")
    val body:String,
    @SerializedName("title")
    val title:String
//    g="https://scontent-maa2-2.xx.fbcdn.net/v/t39.30808-6/375448861_866095595121675_5925679299268803161_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=a2f6c7&_nc_ohc=e8UrEUWWC6UAX-Au-zl&_nc_ht=scontent-maa2-2.xx&oh=00_AfB4t_Tpp4wPvYuzsYrfc4jFQkTb43RUIusfPOdONd-wuw&oe=652900C5"
)
