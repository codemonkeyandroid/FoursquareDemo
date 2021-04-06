package com.codemonkey.android.foursquaredemo.data.remote


import com.codemonkey.android.foursquaredemo.data.remote.response.DetailReponse
import com.codemonkey.android.foursquaredemo.data.remote.response.SearchResponse
import com.codemonkey.android.foursquaredemo.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenuesService {

    @GET("search")
    suspend fun searchVenues(
        @Query("near") locationName: String,
        @Query("client_id") clientId: String = Constants.client_id,
        @Query("client_secret") clientSecret: String = Constants.client_secret,
        @Query("v") date: String = Constants.queryDate,
        @Query("radius") radius: Int = Constants.radius
    ): Response<SearchResponse>

    @GET("{venueId}")
    suspend fun venueDetail(
        @Path("venueId") venueId: String,
        @Query("client_id") clientId: String = Constants.client_id,
        @Query("client_secret") clientSecret: String = Constants.client_secret,
        @Query("v") date: String = Constants.queryDate
    ) : Response<DetailReponse>

}