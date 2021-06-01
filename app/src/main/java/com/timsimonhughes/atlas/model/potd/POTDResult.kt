package com.timsimonhughes.atlas.model.potd

import java.lang.Exception

/**
 * POTDResult from a search, which contains List<Repo> holding query data,
 * and a String of network error state.
 */
sealed class POTDResult {
    data class Success(val data: List<POTD>) : POTDResult()
    data class Error(val error: Exception) : POTDResult()
}