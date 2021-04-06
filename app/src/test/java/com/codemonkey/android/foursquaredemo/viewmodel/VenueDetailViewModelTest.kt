package com.codemonkey.android.foursquaredemo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.codemonkey.android.foursquaredemo.TestCoroutineRule
import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail
import com.codemonkey.android.foursquaredemo.data.repository.IVenuesRepository
import com.codemonkey.android.foursquaredemo.ui.detail.VenueDetailViewModel
import com.codemonkey.android.foursquaredemo.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VenueDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockVenuesRepository: IVenuesRepository

    @Mock
    private lateinit var venueDetailObserver: Observer<Resource<VenueDetail>>

    @Test
    fun givenId_whenLookupVenueDetail_shouldReturnVenueDetail() {
        testCoroutineRule.runBlockingTest {

            //Given
            val id = "123abc"
            val venueDetail = VenueDetail(id, "title")
            val expectedResult =
                MutableLiveData(Resource(status = Resource.Status.SUCCESS, venueDetail, null))
            val sut = VenueDetailViewModel(mockVenuesRepository)

            Mockito.doReturn(expectedResult).`when`(mockVenuesRepository).getVenueDetail(id)
            sut.venueDetail.observeForever(venueDetailObserver)

            // When
            sut.load(id)

            // Then
            Mockito.verify(mockVenuesRepository).getVenueDetail(id)
            Mockito.verify(venueDetailObserver).onChanged(Resource.success(venueDetail))
            sut.venueDetail.removeObserver(venueDetailObserver)
        }

    }


}