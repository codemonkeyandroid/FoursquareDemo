package com.codemonkey.android.foursquaredemo.data.remote.response

import com.codemonkey.android.foursquaredemo.data.entities.Venue
import com.codemonkey.android.foursquaredemo.utils.Constants
import com.codemonkey.android.foursquaredemo.utils.toJSON

data class SearchResponse(val response: SearchVenuesCollection) {

    fun transformToVenueList(query: String): List<Venue> {
        return response.venues.map { Venue(id = it.id,
            title = it.name,
            formattedAddress = it.location.formattedAddress?.toJSON(),
            categoryIcon = it.categories.firstOrNull()?.icon?.let { iconLocData -> "${iconLocData.prefix}${Constants.icon_param}${iconLocData.suffix}" },
            searchQuery = query)
        }
    }

}

data class SearchVenuesCollection(val venues: List<SearchVenue>)

data class SearchVenue(val id: String, val name: String, val location: VenueLocation, val categories: List<SearchVenueCategory>)

data class SearchVenueCategory(val icon: SearchVenueCategoryIcon)

data class SearchVenueCategoryIcon(val prefix: String, val suffix: String)