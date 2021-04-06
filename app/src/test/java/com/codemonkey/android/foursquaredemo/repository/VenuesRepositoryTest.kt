package com.codemonkey.android.foursquaredemo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.codemonkey.android.foursquaredemo.TestCoroutineRule
import com.codemonkey.android.foursquaredemo.data.entities.Venue
import com.codemonkey.android.foursquaredemo.data.local.VenueDetailDao
import com.codemonkey.android.foursquaredemo.data.local.VenueOverviewDao
import com.codemonkey.android.foursquaredemo.data.remote.IVenuesRemoteDataSource
import com.codemonkey.android.foursquaredemo.data.remote.VenuesRemoteDataSource
import com.codemonkey.android.foursquaredemo.data.remote.response.*
import com.codemonkey.android.foursquaredemo.data.repository.VenuesRepository
import com.codemonkey.android.foursquaredemo.ui.overview.VenueOverviewViewModel
import com.codemonkey.android.foursquaredemo.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VenuesRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockVenuesRemoteDataSource: IVenuesRemoteDataSource

    @Mock
    private lateinit var mockVenueOverviewDao: VenueOverviewDao

    @Mock
    private lateinit var mockVenueDetailDao: VenueDetailDao

    @Mock
    private lateinit var repoObserver: Observer<Resource<SearchResponse>>

    @Test
    fun givenQuery_whenSearching_shouldReturnVenues() {
        testCoroutineRule.runBlockingTest {

            //Given
            val mockLocation = "location"
            val sut = VenuesRepository(
                mockVenuesRemoteDataSource,
                mockVenueOverviewDao,
                mockVenueDetailDao
            )
            val expectedResult = DefaultSearchResponse()

            //Mockito.doReturn(expectedResult).`when`(mockVenuesRemoteDataSource).getVenues(mockLocation)
            // Mockito.doReturn(expectedResult).`when`(mockVenueOverviewDao).getVenues(mockLocation)
            // Mockito.doReturn(expectedResult).`when`(mockVenuesRemoteDataSource).getVenues(mockLocation)

            // When
            sut.getVenues(mockLocation)
            //val t = result.value

            verify(mockVenuesRemoteDataSource).getVenues(mockLocation)
            //verify(repoObserver).onChanged(Resource.success(data = expectedResult))
            //Mockito.verify(result).onC

            // Then
            // result.value

        }

    }

    private fun DefaultSearchResponse(): SearchResponse {
        return SearchResponse(
            SearchVenuesCollection(
                mutableListOf(
                    SearchVenue(
                        id = "venue", name = "name", location = VenueLocation(
                            mutableListOf("street", "city", "country")
                        ), categories = mutableListOf(
                            SearchVenueCategory(
                                SearchVenueCategoryIcon(
                                    prefix = "prefix",
                                    suffix = "suffix"
                                )
                            )
                        )
                    )
                )
            )
        )
    }

}