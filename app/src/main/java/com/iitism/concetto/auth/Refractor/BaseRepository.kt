package com.iitism.concetto.auth.Refractor

import android.util.Log
import com.iitism.concetto.retrofit.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        apiCall: suspend()->T
    ):Resource<T>{
        return withContext(Dispatchers.IO){
            try{
                Log.d("resource", Resource.Success(apiCall.invoke()).value.toString())
                Resource.Success(apiCall.invoke())
            }catch (throwable:Throwable){
                when(throwable){
                    is HttpException->{
                        Resource.Failure(false,throwable.code(),throwable.response()?.errorBody())
                    }
                    else->{
                        Resource.Failure(true,null,null)
                    }
                }
            }
        }
    }
}