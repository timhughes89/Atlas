package com.timsimonhughes.atlas.ui.listeners;

import android.view.View;

import com.timsimonhughes.atlas.model.Mission;
import com.timsimonhughes.atlas.model.POTD;

public interface POTDOnItemClickListener {
    void onItemClick(int position, View sharedView, POTD potd);
    //void onMissionItemClick(int position, View sharedView, Mission mission);
}
