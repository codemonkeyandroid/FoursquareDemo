package com.codemonkey.android.foursquaredemo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venue_detail")

data class VenueDetail(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String? = null,
    val formattedAddress: String? = null,
    val phoneNr: String? = null,
    val likes: Int? = null,
    val photos: String? = null,
)