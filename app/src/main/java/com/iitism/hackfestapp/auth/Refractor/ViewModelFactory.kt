package com.iitism.hackfestapp.auth.Refractor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iitism.hackfestapp.changePassword.ChangePassRespo
import com.iitism.hackfestapp.changePassword.ChangeViewModel

class ViewModelFactory(private val repository: BaseRepository,private val context: Context):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when
        {
            modelClass.isAssignableFrom(LoginViewModel::class.java)->LoginViewModel(repository as LoginRepository,context as Context) as T
            modelClass.isAssignableFrom(ChangeViewModel::class.java)->ChangeViewModel(repository as ChangePassRespo)as T
            else-> throw java.lang.IllegalArgumentException("ViewModel not Found")
        }
    }
}