package com.codemonkey.android.foursquaredemo.utils.resources

import android.content.Context
import javax.inject.Inject

class ResourcesProvider @Inject constructor(private val context: Context) : IResourcesProvider {
    override fun getString(id: Int) =  context.getString(id)
}