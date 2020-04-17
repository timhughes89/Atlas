package com.timsimonhughes.atlas.ui.listeners

import android.view.View
import com.timsimonhughes.atlas.model.POTD

interface POTDOnItemClickListener {
    fun onItemClick(position: Int, sharedView: View?, potd: POTD?)
}