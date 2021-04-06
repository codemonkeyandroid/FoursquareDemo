package com.codemonkey.android.foursquaredemo.data.remote.response

import com.codemonkey.android.foursquaredemo.data.entities.VenueDetail
import com.codemonkey.android.foursquaredemo.utils.toJSON

data class DetailReponse(val response: DetailVenue) {

    fun transformToVenueDetail(): VenueDetail {
        return response.venue.let {
            VenueDetail(id = it.id,
                title = it.name,
                description = it.description,
                formattedAddress = it.location.formattedAddress?.toJSON(),
                phoneNr = it.contact.formattedPhone,
                likes = it.likes.count,
                photos = it.photos.groups.firstOrNull()?.items?.map {
                        photo -> "${photo.prefix}${photo.width}x${photo.height}${photo.suffix}"
                }?.toMutableList()?.toJSON()
            )
        }
    }
}

data class DetailVenue(val venue: DetailVenueContent)

data class DetailVenueContent(val id: String, val photos: DetailVenuePhotos, val bestphoto: DetailVenuePhotoItem, val name: String,  val description: String?, val contact: DetailVenueContact, val location: VenueLocation, val likes: DetailVenueLikes)

data class DetailVenueContact(val formattedPhone: String? )

data class DetailVenueLikes(val count: Int)

data class DetailVenuePhotos(val groups: List<DetailVenuePhotoGroups>)

data class DetailVenuePhotoGroups(val items: List<DetailVenuePhotoItem>)

data class DetailVenuePhotoItem(val prefix: String, val suffix: String, val width: Int, val height: Int)