package com.iitism.hackfestapp.ui.adminscanqr

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.integration.android.IntentIntegrator
import com.iitism.hackfestapp.ui.adminscanqr.retrofit.AdminRetrofitInstance
import com.iitism.hackfestapp.ui.adminscanqr.retrofit.adminScanResponse
import com.iitism.hackfestapp.ui.scanqr.retrofit.ScanQrResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class AdminScanQrViewModel(
    val context: Context
) : ViewModel() {


    suspend fun markInOut(scannedtext: String) : Response<adminScanResponse> {
        val response : Response<adminScanResponse> = AdminRetrofitInstance().api.takeAttendance(endpoint = scannedtext)
        if(response.isSuccessful){
            Log.d("Scan qr","${response.body()}")
        }
        else{
            Log.d("Scan qr","Failed ")
        }
        return response
    }


    fun setupScanner(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.setOrientationLocked(false)
    }

    fun performAction(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.initiateScan()
    }
}