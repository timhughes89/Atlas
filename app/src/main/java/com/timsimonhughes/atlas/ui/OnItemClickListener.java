package com.timsimonhughes.atlas.ui;

import android.view.View;

import com.timsimonhughes.atlas.model.POTD;

public interface OnItemClickListener {
    void onItemClick(int position, View sharedView, POTD potd);
}
