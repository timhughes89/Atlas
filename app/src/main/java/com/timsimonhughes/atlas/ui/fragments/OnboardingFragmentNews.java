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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class OnboardingFragmentNews extends Fragment {

    public OnboardingFragmentNews(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_onboarding_news, container, false);

        initUI(view);
        return view;
    }

    private void initUI(View view) {
        TextView textViewTitle1 = view.findViewById(R.id.text_view_onboarding_title_1);
        TextView textViewDescription1 = view.findViewById(R.id.text_view_onboarding_description_1);

        if (getContext().getResources() != null) {
            String[] fragmentTitles = getResources().getStringArray(R.array.onboarding_fragment_titles);
            String[] fragmentDescriptions = getResources().getStringArray(R.array.onboarding_fragment_descriptions);

            textViewTitle1.setText(fragmentTitles[0]);
            textViewDescription1.setText(fragmentDescriptions[0]);
        }
    }

}
