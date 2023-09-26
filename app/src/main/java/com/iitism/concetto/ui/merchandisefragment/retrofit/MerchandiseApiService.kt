package com.iitism.concetto.ui.merchandisefragment.retrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MerchandiseApiService {
    @Multipart
    @POST("api/purchase")
    fun uploadData(
        @Part("data") data: DetailsDataModel,
        @Part image : MultipartBody.Part
    ) : Call<ApiResponse>
}