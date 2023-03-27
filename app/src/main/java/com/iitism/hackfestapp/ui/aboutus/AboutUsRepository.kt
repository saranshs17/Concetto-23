package com.iitism.hackfestapp.ui.aboutus

import com.iitism.hackfestapp.ui.aboutus.retrofit.AboutUsApi

class AboutUsRepository constructor(
    private val api: AboutUsApi
) {
    suspend fun getAllOrganizers() = api.getAllOrganizers()
}