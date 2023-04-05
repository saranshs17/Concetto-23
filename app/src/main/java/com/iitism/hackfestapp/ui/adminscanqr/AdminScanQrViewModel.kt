package com.iitism.hackfestapp.ui.adminscanqr

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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