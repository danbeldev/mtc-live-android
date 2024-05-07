package ru.mtc.live.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mtc.live.data.network.model.Event
import ru.mtc.live.data.network.model.EventDetails
import ru.mtc.live.data.network.model.Venue
import ru.mtc.live.data.network.model.VenueFeature

interface MainNetwork {

    @GET("/mtc-live/v1.0/venues")
    suspend fun getAllVenues(
        @Query("features") features: List<String>
    ): List<Venue>

    @GET("/mtc-live/v1.0/events")
    suspend fun getAllEvents(
        @Query("venueId") venueId: Int
    ): List<Event>

    @GET("/mtc-live/v1.0/events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventDetails?

}

fun createMainNetwork(): MainNetwork = Retrofit.Builder()
    .baseUrl("https://api.danbel.ru:30/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create()