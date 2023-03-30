package com.iitism.hackfestapp.ui.noticeboardfragment.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iitism.hackfestapp.ui.aboutus.AboutUsRepository
import com.iitism.hackfestapp.ui.aboutus.AboutUsViewModel
import com.iitism.hackfestapp.ui.noticeboardfragment.NoticeBoardViewModel

class NoticeBoardViewModelFactory constructor(
    private val repository: AboutUsRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when{
                modelClass.isAssignableFrom(NoticeBoardViewModel::class.java) -> NoticeBoardViewModel(this.repository) as T
                else->throw IllegalArgumentException("ViewModel not Found")
            }
        }

}