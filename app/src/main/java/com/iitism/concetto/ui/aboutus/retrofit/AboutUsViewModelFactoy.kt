package com.iitism.concetto.ui.aboutus.retrofit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iitism.concetto.ui.aboutus.AboutUsRepository
import com.iitism.concetto.ui.aboutus.AboutUsViewModel

class AboutUsViewModelFactoy constructor(
    private val repository: AboutUsRepository,
    private val context : Context
) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AboutUsViewModel::class.java) -> AboutUsViewModel(this.repository,this.context) as T
            else->throw IllegalArgumentException("ViewModel not Found")
        }
    }

}