package com.iitism.concetto.ui.singleevent

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.RetrofitInstanceEvents
import retrofit2.Response

class MyViewModel() : ViewModel() {
    var EventsList = MutableLiveData<SingleEventModel?>()
}