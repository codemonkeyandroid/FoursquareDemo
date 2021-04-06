package com.codemonkey.android.foursquaredemo.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "venues", indices = [Index(value = ["searchQuery"])])

data class Venue(
    @PrimaryKey
    val id: String,
    val categoryIcon: String? = null,
    val title: String,
    val formattedAddress: String? = null,
    val searchQuery: String
)