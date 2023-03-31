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

class ScanQrViewModel (val context:Context, val activity : FragmentActivity?): ViewModel() {

    private var attendanceResponse = MutableLiveData<AttendanceResponse>()



    fun markAttendance(url : String?, id : String?, binding:FragmentScanqrBinding){
        viewModelScope.launch(){
            val scanQrRetrofitInstance = ScanQrRetrofitInstance(url)
                val response = scanQrRetrofitInstance.api.markAttendance(id)
                if(response.isSuccessful){
                    attendanceResponse.postValue(response.body());
                    Log.d("Scan qr","${response.body()}")
                    binding.notmarkedcard.visibility = View.GONE
                    binding.markedcard.visibility = View.VISIBLE
                    binding.loadingCard.visibility = View.GONE
                    Toast.makeText(context,"Attendance Marked",Toast.LENGTH_LONG).show()
                }
                else{
                    binding.loadingCard.visibility= View.GONE
                    Log.d("Scan qr","Failed ")
                }
        }
    }


     fun setupScanner(qrScanIntegrator:IntentIntegrator) {
        qrScanIntegrator.setOrientationLocked(false)
    }

     fun performAction(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.initiateScan()
    }


}