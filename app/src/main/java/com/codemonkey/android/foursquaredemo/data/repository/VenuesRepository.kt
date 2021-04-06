package com.codemonkey.android.foursquaredemo.data.repository

import com.codemonkey.android.foursquaredemo.data.local.VenueDetailDao
import com.codemonkey.android.foursquaredemo.data.local.VenueOverviewDao
import com.codemonkey.android.foursquaredemo.data.remote.IVenuesRemoteDataSource
import com.codemonkey.android.foursquaredemo.utils.performGetOperation
import javax.inject.Inject

class VenuesRepository @Inject constructor(
    private val remoteDataSource: IVenuesRemoteDataSource,
    private val localVenueOverviewDataSource: VenueOverviewDao,
    private val localVenueDetailDataSource: VenueDetailDao
) : IVenuesRepository {

    override fun getVenues(query: String) = performGetOperation(
        databaseQuery = { localVenueOverviewDataSource.getVenues(query) },
        networkCall = { remoteDataSource.getVenues(query) },
        saveCallResult = { localVenueOverviewDataSource.insertAll(it.transformToVenueList(query)) }
    )

    override fun getVenueDetail(id: String) = performGetOperation(
        databaseQuery = { localVenueDetailDataSource.getVenueDetail(id) },
        networkCall = { remoteDataSource.getVenueDetail(id) },
        saveCallResult = { localVenueDetailDataSource.insert(it.transformToVenueDetail()) }
    )
}