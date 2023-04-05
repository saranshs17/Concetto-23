package com.iitism.hackfestapp.ui.scanqr

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.iitism.hackfestapp.ui.scanqr.retrofit.ScanQrResponse
import com.iitism.hackfestapp.ui.scanqr.retrofit.ScanQrRetrofitInstance
import retrofit2.Response

class ScanQrViewModel (val context:Context, val activity : FragmentActivity?): ViewModel() {

    private var scanQrResponse = MutableLiveData<ScanQrResponse>()
    var attendance_count  = MutableLiveData<Int>(0);


    suspend fun markAttendance(id : String?,baseurl : String) : Response<ScanQrResponse>{

        val scanQrRetrofitInstance = ScanQrRetrofitInstance(baseurl)
        val response : Response<ScanQrResponse> = scanQrRetrofitInstance.api.markAttendance(id)
        if(response.isSuccessful){
            Log.d("Scan qr","${response.body()}")
        }
        else{
            Log.d("Scan qr","Failed ")
        }
        return response

    }


     fun setupScanner(qrScanIntegrator:IntentIntegrator) {
        qrScanIntegrator.setOrientationLocked(false)
    }

     fun performAction(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.initiateScan()
    }


}