package com.example.mediaplayer.model.response

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class Item(
    val art: String,
    val description: String,
    val director: String,
    val duration: Int,
    val id: String,
    val ratings: Any,
    val title: String,
    val url: String,
    val year: Int
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }
}