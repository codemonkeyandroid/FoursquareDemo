package com.codemonkey.android.foursquaredemo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail

@Dao
interface VenueDetailDao {

    @Query("SELECT * FROM venue_detail WHERE id = :id")
    fun getVenueDetail(id: String) : LiveData<VenueDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venueDetail: VenueDetail)
}