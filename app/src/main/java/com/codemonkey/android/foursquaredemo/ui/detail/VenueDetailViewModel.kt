package com.codemonkey.android.foursquaredemo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.codemonkey.android.foursquaredemo.R
import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail
import com.codemonkey.android.foursquaredemo.data.repository.IVenuesRepository
import com.codemonkey.android.foursquaredemo.utils.Resource
import com.codemonkey.android.foursquaredemo.utils.fromJSON
import com.codemonkey.android.foursquaredemo.utils.parseFormatAddress
import com.codemonkey.android.foursquaredemo.utils.resources.IResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VenueDetailViewModel @Inject constructor(private val repository: IVenuesRepository, private val res: IResourcesProvider) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _venueDetail = _id.switchMap { id ->
        repository.getVenueDetail(id)
    }

    val venueDetail: LiveData<Resource<VenueDetail>> = _venueDetail

    val title = _venueDetail.switchMap { d -> MutableLiveData(d.data?.title ?: "")}
    val description = _venueDetail.switchMap { d -> MutableLiveData(d.data?.description ?: "")}
    val address = _venueDetail.switchMap { d -> MutableLiveData(d.data?.formattedAddress?.fromJSON()?.parseFormatAddress() ?: "" )}
    val likes = _venueDetail.switchMap { d -> MutableLiveData(d.data?.likes?.let { "${res.getString(R.string.likes)}$it" } ?: "" )}
    val phoneNr = _venueDetail.switchMap { d -> MutableLiveData(d.data?.phoneNr?.let { "${res.getString(R.string.phonenr)}$it" } ?: "" )}
    val photos = _venueDetail.switchMap { d -> MutableLiveData(d.data?.photos?.fromJSON() ?: emptyList()) }

    fun load(id: String) {
        _id.value = id
    }
}