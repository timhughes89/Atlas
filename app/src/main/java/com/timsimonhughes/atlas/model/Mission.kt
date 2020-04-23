package com.timsimonhughes.atlas.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Mission(
    @field:Json(name = "missionUrl")
    val missionUrl: String?,
    @field:Json(name = "missionTitle")
    val missionTitle: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            missionUrl = parcel.readString(),
            missionTitle = parcel.readString()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(missionUrl)
        dest?.writeString(missionTitle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mission> {
        override fun createFromParcel(parcel: Parcel): Mission {
            return Mission(parcel)
        }

        override fun newArray(size: Int): Array<Mission?> {
            return arrayOfNulls(size)
        }
    }
}