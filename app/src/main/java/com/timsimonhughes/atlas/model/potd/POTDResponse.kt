package com.timsimonhughes.atlas.model.potd

import com.google.gson.annotations.SerializedName

data class POTDResponse(
    @SerializedName("items") val results: List<POTD>
)


