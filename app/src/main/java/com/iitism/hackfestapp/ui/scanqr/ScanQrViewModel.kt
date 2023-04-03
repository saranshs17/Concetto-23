package com.iitism.hackfestapp.ui.scanqr

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.integration.android.IntentIntegrator
import com.iitism.hackfestapp.databinding.FragmentAdminScanQrBinding
import com.iitism.hackfestapp.databinding.FragmentScanqrBinding
import com.iitism.hackfestapp.ui.scanqr.retrofit.AttendanceResponse
import com.iitism.hackfestapp.ui.scanqr.retrofit.ScanQrRetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ScanQrViewModel (val context:Context, val activity : FragmentActivity?): ViewModel() {

    private var attendanceResponse = MutableLiveData<AttendanceResponse>()
    var attendance_count  = MutableLiveData<Int>(0);


    suspend fun markAttendance(id : String?,baseurl : String) : Response<AttendanceResponse>{

        val scanQrRetrofitInstance = ScanQrRetrofitInstance(baseurl)
        val response : Response<AttendanceResponse> = scanQrRetrofitInstance.api.markAttendance(id)
        if(response.isSuccessful){
            attendanceResponse.postValue(response.body());
            Log.d("Scan qr","${response.body()}")
            attendance_count.value = attendance_count.value?.plus(1)
        }
        else{
//           binding.loadingCard.visibility= View.GONE
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