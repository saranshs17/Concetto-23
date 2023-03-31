package com.iitism.hackfestapp.ui.adminscanqr

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.integration.android.IntentIntegrator
import com.iitism.hackfestapp.ui.adminscanqr.retrofit.AdminRetrofitInstance
import kotlinx.coroutines.launch

class AdminScanQrViewModel(
    val context : Context
) : ViewModel() {

     var ID : String = ""
     var Task : String = ""
    private var result = MutableLiveData<String>()

    fun markInOut(scannedtext: String) = viewModelScope.launch{
        val retrofitInstance = AdminRetrofitInstance()
        viewModelScope.launch {
            val response = retrofitInstance.api.takeAttendance(endpoint = scannedtext)
            if(response.isSuccessful){
                Log.d("Admin Scan Qr","${response.body()}")
                Toast.makeText(context,"${response.body()}",Toast.LENGTH_SHORT).show()
                result.postValue(response.body())
            }
            else{
                Log.d("Admin Scan Qr","${response.body()}")
            }
        }

    }







    fun setupScanner(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.setOrientationLocked(false)
    }

    fun performAction(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.initiateScan()
    }
}