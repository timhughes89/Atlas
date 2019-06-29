package com.timsimonhughes.atlas.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timsimonhughes.atlas.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnboardingFragmentPhotos extends Fragment {

    public OnboardingFragmentPhotos() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_onboarding_photos, container, false);

        initUI(view);
        return view;
    }

    private void initUI(View view) {
        TextView textViewTitle3 = view.findViewById(R.id.text_view_onboarding_title_3);
        TextView textViewDescription3 = view.findViewById(R.id.text_view_onboarding_description_3);

        if (getContext().getResources() != null) {
            String[] fragmentTitles = getResources().getStringArray(R.array.onboarding_fragment_titles);
            String[] fragmentDescriptions = getResources().getStringArray(R.array.onboarding_fragment_descriptions);

            textViewTitle3.setText(fragmentTitles[2]);
            textViewDescription3.setText(fragmentDescriptions[2]);
        }
    }
}
