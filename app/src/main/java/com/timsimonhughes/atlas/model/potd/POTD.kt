package com.timsimonhughes.atlas.model.potd

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.timsimonhughes.atlas.common.enums.NetworkState

@Entity(tableName = "photosOfDay")
data class POTD(
        @field:SerializedName( "date")
        val date: String?,
        @field:SerializedName( "explanation")
        val explanation: String?,
        @field:SerializedName( "hdurl")
        val hrUrl: String?,
        @field:SerializedName( "media_type")
        val mediaType: String?,
        @field:SerializedName( "service_version")
        val serviceVersion: String?,
        @PrimaryKey
        @field:SerializedName( "title")
        val title: String,
        @field:SerializedName( "url")
        val url: String?
)

data class POTDListing<T>(
// the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
        // represents the network request status to show to the user
    val networkState: LiveData<NetworkState>,
        // represents the refresh status to show to the user. Separate from networkState, this
        // value is importantly only when refresh is requested.
    val refreshState: LiveData<NetworkState>,
        // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit,
        // retries any failed requests.
    val retry: () -> Unit
)