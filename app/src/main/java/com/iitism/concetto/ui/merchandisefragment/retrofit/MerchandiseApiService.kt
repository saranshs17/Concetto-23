package com.iitism.concetto.ui.merchandisefragment.retrofit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface MerchandiseApiService {
    @POST("api/purchase")
    fun uploadData(@Body data: DetailsDataModel) : Call<ApiResponse?>
}