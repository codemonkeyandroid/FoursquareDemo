package com.codemonkey.android.foursquaredemo.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun List<String>.toJSON() : String = Gson().toJson(this)

fun String.fromJSON(): List<String> = Gson().fromJson(this, object : TypeToken<List<String>>() {}.type)

fun List<String>?.parseFormatAddress(): String = this?.joinToString { "${it}\n" }?.replace(", ","") ?: ""
