package com.iitism.hackfestapp.ui.scanqr

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScanQrViewModelFactory(
    private val context: Context,
private val activity: FragmentActivity?
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ScanQrViewModel::class.java) -> ScanQrViewModel(this.context as Context,this.activity as FragmentActivity?) as T
            else -> throw IllegalArgumentException("Scan Qr Viewmodel not found")
        }

    }
}