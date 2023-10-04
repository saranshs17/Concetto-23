package com.iitism.concetto.ui.registrationEvent

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iitism.concetto.ui.registrationEvent.retrofit.ApiResponseForCriteria
import com.iitism.concetto.ui.registrationEvent.retrofit.RetrofitInstance
import com.iitism.concetto.ui.singleevent.SingleEventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class RegisterViewModel(
    private val context: Context,
    private val id : String
) : ViewModel() {
    val retrofit = RetrofitInstance()
    val criterialList = MutableLiveData<ApiResponseForCriteria?>()

    fun fetchData() : Boolean
    {
        var fl = false
        GlobalScope.launch(Dispatchers.Main){
            try{
                val url = "api/events/" + id
                val response = retrofit.apiService.getCriteria(url)
                if(response.isSuccessful)
                {
                    val data = response.body()
                    if(data != null)
                    {
                        Log.i("Boolean Data",data.toString())
                        criterialList.postValue(data)
                        fl = true
                    }
                }
                else {
                    Log.e("API Error", "Response code: ${response.code()}")
                }
            }
            catch (e : Exception)
            {
                Log.e("Error", e.toString())
            }
        }
        return fl
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