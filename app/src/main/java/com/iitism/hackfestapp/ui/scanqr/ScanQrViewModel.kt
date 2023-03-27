package com.iitism.hackfestapp.ui.scanqr

import androidx.lifecycle.ViewModel
import com.google.zxing.integration.android.IntentIntegrator

class ScanQrViewModel : ViewModel() {



     fun setupScanner(qrScanIntegrator:IntentIntegrator) {
        qrScanIntegrator.setOrientationLocked(false)
    }

     fun performAction(qrScanIntegrator: IntentIntegrator) {
        qrScanIntegrator.initiateScan()
    }


}