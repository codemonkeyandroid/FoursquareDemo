package com.codemonkey.android.foursquaredemo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.codemonkey.android.foursquaredemo.R
import com.codemonkey.android.foursquaredemo.TestCoroutineRule
import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail
import com.codemonkey.android.foursquaredemo.data.repository.IVenuesRepository
import com.codemonkey.android.foursquaredemo.ui.detail.VenueDetailViewModel
import com.codemonkey.android.foursquaredemo.utils.Resource
import com.codemonkey.android.foursquaredemo.utils.resources.IResourcesProvider
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
    private lateinit var mockResourcesProvider: IResourcesProvider

    @Mock
    private lateinit var venueDetailObserver: Observer<Resource<VenueDetail>>

    @Mock
    private lateinit var titleObserver: Observer<String>

    @Mock
    private lateinit var descriptionObserver: Observer<String>

    @Mock
    private lateinit var addressObserver: Observer<String>

    @Mock
    private lateinit var phoneObserver: Observer<String>

    @Mock
    private lateinit var likesObserver: Observer<String>

    @Mock
    private lateinit var photosObserver: Observer<List<String>>

    @Test
    fun givenId_whenLookupVenueDetailWithNoData_shouldReturnVenueDetailWithBlanks() {
        testCoroutineRule.runBlockingTest {

            //Given
            val id = "123abc"
            val venueDetail = VenueDetail(id = id, title = "title")
            val expectedResult = MutableLiveData(Resource(status = Resource.Status.SUCCESS, venueDetail, null))
            val sut = VenueDetailViewModel(mockVenuesRepository, mockResourcesProvider)

            Mockito.doReturn(expectedResult).`when`(mockVenuesRepository).getVenueDetail(id)

            setObservers(sut)

            // When
            sut.load(id)

            // Then
            Mockito.verify(mockVenuesRepository).getVenueDetail(id)
            Mockito.verify(venueDetailObserver).onChanged(Resource.success(venueDetail))
            Mockito.verify(titleObserver).onChanged("title")
            Mockito.verify(descriptionObserver).onChanged("")
            Mockito.verify(addressObserver).onChanged("")
            Mockito.verify(phoneObserver).onChanged("")
            Mockito.verify(likesObserver).onChanged("")
            Mockito.verify(photosObserver).onChanged(emptyList())

            sut.venueDetail.removeObserver(venueDetailObserver)
        }
    }

    @Test
    fun givenId_whenLookupVenueDetailWithNoData_shouldReturnFormattedVenueDetail() {
        testCoroutineRule.runBlockingTest {

            //Given
            val id = "123abc"
            val venueDetail = VenueDetail(id = id, title = "title", description = "description", formattedAddress = "[\"something1\", \"something2\"]", phoneNr = "555-555-123", likes = 10, photos = "[\"www.image.com/image1.png\", \"www.image.com/image2.png\"]")

            val expectedResult = MutableLiveData(Resource(status = Resource.Status.SUCCESS, venueDetail, null))
            val sut = VenueDetailViewModel(mockVenuesRepository, mockResourcesProvider)

            Mockito.doReturn(expectedResult).`when`(mockVenuesRepository).getVenueDetail(id)
            Mockito.doReturn("Phone: ").`when`(mockResourcesProvider).getString(R.string.phonenr)
            Mockito.doReturn("Likes: ").`when`(mockResourcesProvider).getString(R.string.likes)

            setObservers(sut)

            // When
            sut.load(id)

            // Then
            Mockito.verify(mockVenuesRepository).getVenueDetail(id)
            Mockito.verify(venueDetailObserver).onChanged(Resource.success(venueDetail))
            Mockito.verify(titleObserver).onChanged("title")
            Mockito.verify(descriptionObserver).onChanged("description")
            Mockito.verify(addressObserver).onChanged("something1\nsomething2\n")
            Mockito.verify(phoneObserver).onChanged("Phone: 555-555-123")
            Mockito.verify(likesObserver).onChanged("Likes: 10")
            Mockito.verify(photosObserver).onChanged(mutableListOf("www.image.com/image1.png", "www.image.com/image2.png"))

            sut.venueDetail.removeObserver(venueDetailObserver)
        }

    }


    private fun setObservers(sut: VenueDetailViewModel) {
        sut.apply {
            venueDetail.observeForever(venueDetailObserver)
            title.observeForever(titleObserver)
            description.observeForever(descriptionObserver)
            address.observeForever(addressObserver)
            phoneNr.observeForever(phoneObserver)
            likes.observeForever(likesObserver)
            photos.observeForever(photosObserver)
        }
    }

    private fun removeObservers(sut: VenueDetailViewModel) {
        sut.apply {
            venueDetail.removeObserver(venueDetailObserver)
            title.removeObserver(titleObserver)
            description.removeObserver(descriptionObserver)
            address.removeObserver(addressObserver)
            phoneNr.removeObserver(phoneObserver)
            likes.removeObserver(likesObserver)
            photos.removeObserver(photosObserver)
        }
    }
}