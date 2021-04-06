package com.codemonkey.android.foursquaredemo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.codemonkey.android.foursquaredemo.TestCoroutineRule
import com.codemonkey.android.foursquaredemo.data.entities.Venue
import com.codemonkey.android.foursquaredemo.data.repository.IVenuesRepository
import com.codemonkey.android.foursquaredemo.ui.overview.VenueOverviewViewModel
import com.codemonkey.android.foursquaredemo.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VenueOverviewViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockVenuesRepository: IVenuesRepository

    @Mock
    private lateinit var venuesObserver: Observer<Resource<List<Venue>>>

    @Test
    fun givenQuery_whenSearching_shouldReturnVenues() {
        testCoroutineRule.runBlockingTest {

            //Given
            val query = "Amsterdam"
            val venueList = mutableListOf(Venue(id = "id", title = "title", searchQuery = query))
            val expectedResult = MutableLiveData(Resource(status = Resource.Status.SUCCESS, venueList, null))
            val sut = VenueOverviewViewModel(mockVenuesRepository)

            doReturn(expectedResult).`when`(mockVenuesRepository).getVenues(query)
            sut.venues.observeForever(venuesObserver)

            // When
            sut.load(query)

            // Then
            verify(mockVenuesRepository).getVenues(query)
            verify(venuesObserver).onChanged(Resource.success(venueList))
            sut.venues.removeObserver(venuesObserver)
        }

    }

    @Test
    fun givenEmptyQuery_whenSearching_shouldNotInteract() {
        testCoroutineRule.runBlockingTest {

            //Given
            val query = ""
            val sut = VenueOverviewViewModel(mockVenuesRepository)

            // When
            sut.load(query)

            // Then
            verifyNoInteractions(mockVenuesRepository)
        }

    }

}