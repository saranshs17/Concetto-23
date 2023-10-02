package com.iitism.concetto.ui.clubevents

import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ClubEventAPIService {
    @GET("api/showAllEvents")
    suspend fun getAllEvents(): Response<List<Club_dataclass>>

}