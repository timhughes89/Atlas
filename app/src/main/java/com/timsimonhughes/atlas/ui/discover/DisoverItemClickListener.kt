package com.timsimonhughes.atlas.ui.discover

import android.view.View
import com.timsimonhughes.atlas.model.Planet

interface DisoverItemClickListener {
    fun onItemClick(position: Int, sharedView: View?, planet: Planet?)
}