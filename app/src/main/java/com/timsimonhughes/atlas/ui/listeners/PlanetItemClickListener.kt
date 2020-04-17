package com.timsimonhughes.atlas.ui.listeners

import android.view.View
import com.timsimonhughes.atlas.model.Planet

interface PlanetItemClickListener {
    fun onItemClick(position: Int, sharedView: View?, planet: Planet?)
}