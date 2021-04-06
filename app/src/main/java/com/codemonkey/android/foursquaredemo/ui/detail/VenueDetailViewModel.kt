package com.codemonkey.android.foursquaredemo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail
import com.codemonkey.android.foursquaredemo.data.repository.IVenuesRepository
import com.codemonkey.android.foursquaredemo.data.repository.VenuesRepository
import com.codemonkey.android.foursquaredemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VenueDetailViewModel @Inject constructor(private val repository: IVenuesRepository) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _venueDetail = _id.switchMap { id ->
        repository.getVenueDetail(id)
    }

    val venueDetail: LiveData<Resource<VenueDetail>> = _venueDetail

    fun load(id: String) {
        _id.value = id
    }

}