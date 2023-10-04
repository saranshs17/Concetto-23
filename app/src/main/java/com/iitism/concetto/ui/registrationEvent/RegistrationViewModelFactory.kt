package com.iitism.concetto.ui.registrationEvent

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iitism.concetto.ui.singleevent.MyViewModel

class RegistrationViewModelFactory constructor(
    private val context : Context,
    private val id : String
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(this.context,this.id) as T
            else->throw IllegalArgumentException("ViewModel not Found")
        }
    }
}