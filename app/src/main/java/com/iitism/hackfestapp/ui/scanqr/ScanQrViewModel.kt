package com.iitism.hackfestapp.ui.scanqr

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }


}