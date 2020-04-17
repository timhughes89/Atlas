package com.timsimonhughes.atlas.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Planet(
        @field:Json(name = "title")
        val title: String?,
        @field:Json(name = "image_url")
        val imageUrl: String?,
        @field:Json(name = "overview")
        val overview: String?,
        @field:Json(name = "orbital_period")
        val orbitalPeriod: String?,
        @field:Json(name = "distance_sol")
        val distanceSol: String?,
        @field:Json(name = "moons")
        val moons: String?,
        @field:Json(name = "orbital_speed")
        val orbitalSpeed: Float,
        @field:Json(name = "planet_type")
        val planetType: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeString(overview)
        parcel.writeString(orbitalPeriod)
        parcel.writeString(distanceSol)
        parcel.writeString(moons)
        parcel.writeFloat(orbitalSpeed)
        parcel.writeString(planetType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Planet> {
        override fun createFromParcel(parcel: Parcel): Planet {
            return Planet(parcel)
        }

        override fun newArray(size: Int): Array<Planet?> {
            return arrayOfNulls(size)
        }
    }
}