package com.iitism.concetto.ui.aboutUs

import com.iitism.concetto.ui.aboutUs.retrofit.AboutUsApi

class AboutUsRepository constructor(private val api: AboutUsApi) {
    suspend fun getAllOrganizers() = api.getAllOrganizers()

    suspend fun getAllNotices() = api.getAllNotices()
}