package com.example.hackfestapp.ui.aboutus.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackfestapp.ui.aboutus.AboutUsModel
import com.example.hackfestapp.ui.aboutus.AboutUsRepository
import com.example.hackfestapp.ui.aboutus.AboutUsViewModel

class AboutUsViewModelFactoy constructor(
    private val repository: AboutUsRepository
) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AboutUsViewModel::class.java) -> AboutUsViewModel(this.repository) as T
            else->throw IllegalArgumentException("ViewModel not Found")
        }
    }

}