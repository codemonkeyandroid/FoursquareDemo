package com.codemonkey.android.foursquaredemo.data.remote

import javax.inject.Inject

class VenuesRemoteDataSource @Inject constructor(
    private val venuesService: VenuesService
): BaseDataSource(), IVenuesRemoteDataSource {

    override suspend fun getVenues(locationName: String) = getResult { venuesService.searchVenues(locationName = locationName) }

    override suspend fun getVenueDetail(id: String) = getResult { venuesService.venueDetail(venueId = id) }
}