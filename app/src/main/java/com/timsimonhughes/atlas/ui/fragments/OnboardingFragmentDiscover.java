package com.timsimonhughes.atlas.ui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.timsimonhughes.atlas.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnboardingFragmentDiscover extends Fragment {

    public OnboardingFragmentDiscover(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_onboarding_discover, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        ImageView imageViewPlanet1 = view.findViewById(R.id.image_view_1);
        ImageView imageViewPlanet2 = view.findViewById(R.id.image_view_2);
        ImageView imageViewPlanet3 = view.findViewById(R.id.image_view_3);

        TextView textViewTitle2 = view.findViewById(R.id.text_view_onboarding_title_2);
        TextView textViewDescription2 = view.findViewById(R.id.text_view_onboarding_description_2);

        if (getContext().getResources() != null) {
            String[] fragmentTitles = getResources().getStringArray(R.array.onboarding_fragment_titles);
            String[] fragmentDescriptions = getResources().getStringArray(R.array.onboarding_fragment_descriptions);

            textViewTitle2.setText(fragmentTitles[1]);
            textViewDescription2.setText(fragmentDescriptions[1]);
        }
    }
}
