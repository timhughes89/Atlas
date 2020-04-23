package com.timsimonhughes.atlas.ui.potd

import android.view.View
import com.timsimonhughes.atlas.model.POTD

interface POTDItemClickListener {
    fun onItemClick(position: Int, sharedView: View?, potd: POTD?)
}