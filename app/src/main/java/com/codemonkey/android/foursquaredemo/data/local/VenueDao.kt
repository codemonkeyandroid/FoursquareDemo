package com.codemonkey.android.foursquaredemo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codemonkey.android.foursquaredemo.data.entities.Venue
import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail

@Dao
interface VenueOverviewDao {
    @Query("SELECT * FROM venues WHERE searchQuery = :query")
    fun getVenues(query: String): LiveData<List<Venue>>

    @Query("SELECT * FROM venues WHERE id = :id")
    fun getVenue(id: String): LiveData<Venue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(venues: List<Venue>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venue: Venue)

}