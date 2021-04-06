package com.codemonkey.android.foursquaredemo.ui.overview


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.codemonkey.android.foursquaredemo.data.entities.Venue
import com.codemonkey.android.foursquaredemo.data.repository.IVenuesRepository
import com.codemonkey.android.foursquaredemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VenueOverviewViewModel @Inject constructor(private val repository: IVenuesRepository) :
    ViewModel() {

    private val _query = MutableLiveData<String>()

    private val _venues = _query.switchMap { query ->
        repository.getVenues(query)
    }
    val venues: LiveData<Resource<List<Venue>>> = _venues

    fun load(query: String?) {
        query?.let {
            if(it.isEmpty()) return
            _query.value = it
        }
    }
}