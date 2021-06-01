package com.timsimonhughes.network.model

import com.google.gson.annotations.SerializedName
import com.timsimonhughes.network.model.POTD

data class POTDResponse(
    @SerializedName("items") val results: List<POTD>
)


