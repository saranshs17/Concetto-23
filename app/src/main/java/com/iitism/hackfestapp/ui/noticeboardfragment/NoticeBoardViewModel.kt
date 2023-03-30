package com.iitism.hackfestapp.ui.noticeboardfragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iitism.hackfestapp.ui.aboutus.AboutUsRepository

class NoticeBoardViewModel(private val repository: AboutUsRepository) : ViewModel() {
    val list = MutableLiveData<List<NoticeBoardModel>>()


    suspend fun getAllOrganizers(){
        val response = repository.getAllNotices()

        if (response.isSuccessful){
            list.postValue(response.body())
        }
        else{
            Log.d("tag","Retrofit : Bad Response")
        }
    }
}