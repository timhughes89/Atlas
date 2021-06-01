package com.timsimonhughes.atlas.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Mission(
    @field:Json(name = "missionUrl")
    val missionUrl: String?,
    @field:Json(name = "missionTitle")
    val missionTitle: String?
)