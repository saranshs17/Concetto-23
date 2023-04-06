package com.iitism.concetto.ui.adminscanqr.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AdminScanQrApi {
    @GET("{endpoint}")
    suspend fun takeAttendance (
        @Path(value = "endpoint",encoded = true)endpoint:String?
    ): Response<adminScanResponse>

}