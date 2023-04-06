package com.iitism.concetto.ui.problemstatement

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iitism.concetto.ui.aboutus.AboutUsRepository

class ProblemStatementViewModel(private val repository: AboutUsRepository) : ViewModel() {
    val list = MutableLiveData<List<ProblemStatementModel>>()


    suspend fun getAllProblems(){
        val response = repository.getAllProblems()

        if (response.isSuccessful){
            list.postValue(response.body())
        }
        else{
            Log.d("tag","Retrofit : Bad Response")
        }
    }
}