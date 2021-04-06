package com.codemonkey.android.foursquaredemo.data.repository

import androidx.lifecycle.LiveData
import com.codemonkey.android.foursquaredemo.data.entities.Venue
import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail
import com.codemonkey.android.foursquaredemo.utils.Resource

interface IVenuesRepository {
    fun getVenues(query: String): LiveData<Resource<List<Venue>>>
    fun getVenueDetail(id: String): LiveData<Resource<VenueDetail>>
}