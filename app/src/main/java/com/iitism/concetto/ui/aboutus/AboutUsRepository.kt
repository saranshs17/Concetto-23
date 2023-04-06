package com.iitism.concetto.ui.aboutus

import com.iitism.concetto.ui.aboutus.retrofit.AboutUsApi

class AboutUsRepository constructor(private val api: AboutUsApi) {
    suspend fun getAllOrganizers() = api.getAllOrganizers()

    suspend fun getAllNotices() = api.getAllNotices()

    suspend fun getAllProblems() = api.getAllProblems()

}