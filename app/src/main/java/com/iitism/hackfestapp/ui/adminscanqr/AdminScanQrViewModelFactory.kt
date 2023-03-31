package com.iitism.hackfestapp.ui.adminscanqr

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminScanQrViewModelFactory(private val context : Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AdminScanQrViewModel::class.java) -> AdminScanQrViewModel(this.context as Context) as T
            else -> throw java.lang.IllegalArgumentException("AdminScanQr ViewModel not found")
        }
    }

}