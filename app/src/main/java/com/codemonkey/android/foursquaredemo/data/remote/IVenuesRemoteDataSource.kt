package com.codemonkey.android.foursquaredemo.data.remote

import com.codemonkey.android.foursquaredemo.data.remote.response.DetailReponse
import com.codemonkey.android.foursquaredemo.data.remote.response.SearchResponse
import com.codemonkey.android.foursquaredemo.utils.Resource

interface IVenuesRemoteDataSource {
    suspend fun getVenues(locationName: String): Resource<SearchResponse>
    suspend fun getVenueDetail(id: String): Resource<DetailReponse>
}