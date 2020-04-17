package com.timsimonhughes.atlas.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class POTD(
        @field:Json(name = "date")
        val date: String?,
        @field:Json(name = "explanation")
        val explanation: String?,
        @field:Json(name = "hdurl")
        val hrUrl: String?,
        @field:Json(name = "media_type")
        val mediaType: String?,
        @field:Json(name = "service_version")
        val serviceVersion: String?,
        @field:Json(name = "title")
        val title: String?,
        @field:Json(name = "url")
        val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(explanation)
        parcel.writeString(hrUrl)
        parcel.writeString(mediaType)
        parcel.writeString(serviceVersion)
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<POTD> {
        override fun createFromParcel(parcel: Parcel): POTD {
            return POTD(parcel)
        }

        override fun newArray(size: Int): Array<POTD?> {
            return arrayOfNulls(size)
        }
    }
}