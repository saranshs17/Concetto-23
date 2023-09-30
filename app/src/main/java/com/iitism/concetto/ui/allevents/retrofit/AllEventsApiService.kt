package com.iitism.concetto.ui.allevents.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface AllEventsApiService {
    @GET("api/showAllEvents")
    suspend fun getAllEvents(): Response<List<AllEventsDataModel>>

}