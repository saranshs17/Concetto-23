package com.example.hackfestapp.ui.scanqr

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

class ScanQrViewModel : ViewModel() {



     fun setupScanner(qrScanIntegrator:IntentIntegrator) {
        qrScanIntegrator.setOrientationLocked(false)
    }

     fun performAction(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.initiateScan()
    }


}