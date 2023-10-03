package com.iitism.concetto.ui.singleevent

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory constructor(
    private val context : Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MyViewModel::class.java) -> MyViewModel(
                this.context,
            ) as T
            else->throw IllegalArgumentException("ViewModel not Found")
        }
    }

}