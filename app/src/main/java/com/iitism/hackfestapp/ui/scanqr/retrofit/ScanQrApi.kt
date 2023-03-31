package com.iitism.hackfestapp.ui.scanqr.retrofit

import android.hardware.usb.UsbEndpoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ScanQrApi {

    @GET("{endpoint}")
    suspend fun markAttendance(
        @Path(value = "endpoint",encoded = true) endpoint: String?
    ) : Response<AttendanceResponse>
}

