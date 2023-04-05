package com.iitism.hackfestapp.ui.adminscanqr.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface AdminScanQrApi {
    @GET("{endpoint}")
    suspend fun takeAttendance (
        @Path(value = "endpoint",encoded = true)endpoint:String?
    ): Response<adminScanResponse>

}