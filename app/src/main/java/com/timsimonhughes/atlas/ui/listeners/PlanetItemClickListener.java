package com.timsimonhughes.atlas.ui.listeners;

import android.view.View;

import com.timsimonhughes.atlas.model.Mission;
import com.timsimonhughes.atlas.model.Planet;

public interface PlanetItemClickListener {
    void onItemClick(int position, View sharedView, Planet planet);
}
