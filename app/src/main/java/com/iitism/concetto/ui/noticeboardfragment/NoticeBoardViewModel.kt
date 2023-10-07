package com.iitism.concetto.ui.noticeboardfragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.ktx.remoteMessage
import com.iitism.concetto.ui.aboutUs.AboutUsRepository

class NoticeBoardViewModel(private val repository: AboutUsRepository,
                           private val context: Context
) : ViewModel() {
    val list = MutableLiveData<List<NoticeBoardModel>>()


    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getAllOrganizers(){
        val response = repository.getAllNotices()
        Log.d("Notices-Response",response.body().toString())

        if (response.isSuccessful){
            //list.value?.toMutableList()?.clear()
            list.postValue(response.body())
//            list.value?.toMutableList().apply {
//                this?.forEach {
//                    if(it.title==""||it.message==""){
//                        remove(it)
//                    }
//                }
//            }
            for(item in response.body()!!.asIterable()){
                Log.d("Notices-Response",item.title+" "+item.message)
            }
        }
        else{
            Log.d("tag","Retrofit : Bad Response")
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}