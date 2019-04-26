package com.timsimonhughes.atlas.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timsimonhughes.atlas.Constants;
import com.timsimonhughes.atlas.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SplashFragment extends Fragment {

    private FragmentManager fragmentManager;

    public SplashFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        delayLoad();
    }

    private void delayLoad() {
        new Handler().postDelayed(() -> {
            loadFragment();
        }, 3000);
    }

    private void loadFragment() {

        fragmentManager = getFragmentManager();

        if (getContext() != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(Constants.FIRST_RUN)) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_slide_in, R.anim.fade_slide_out)
                        .replace(R.id.container, new MainFragment())
                        .commit();
            } else {
                sharedPreferences.edit().putString(Constants.FIRST_RUN, "first_run").apply();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_slide_in, R.anim.fade_slide_out)
                        .replace(R.id.container, new OnboardingFragment())
                        .commit();
            }
        }
    }
}
