package com.timsimonhughes.atlas.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.ui.adapters.OnboardingPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class OnboardingFragment extends Fragment {

    private View view;
    private ViewPager onboardingViewPager;
    private ImageButton imageButtonPrevious, imageButtonNext;
    private OnboardingPagerAdapter onboardingPagerAdapter;
    private int pagePosition = 0;
    private int pageCount;


    public OnboardingFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_onboarding, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
        setAdapter();
    }

    private void initUI() {
        imageButtonNext = view.findViewById(R.id.button_next);
        imageButtonPrevious = view.findViewById(R.id.button_previous);
        imageButtonPrevious.setVisibility(View.INVISIBLE);
        onboardingViewPager = view.findViewById(R.id.view_pager_onboarding);
    }

    private void setAdapter() {
        onboardingPagerAdapter = new OnboardingPagerAdapter(getContext());
        onboardingViewPager.setAdapter(onboardingPagerAdapter);
        onboardingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
                onboardingViewPager.setCurrentItem(pagePosition);

                if (pagePosition == 0) {
                    imageButtonNext.setVisibility(View.VISIBLE);
                    imageButtonPrevious.setVisibility(View.INVISIBLE);
                } else if (pagePosition == pageCount - 1) {
                    imageButtonNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                } else {
                    imageButtonPrevious.setVisibility(View.VISIBLE);
                    imageButtonNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pageCount = onboardingPagerAdapter.getCount();

        imageButtonNext.setOnClickListener(v -> {
            if (pagePosition < pageCount - 1) {
                pagePosition ++;
                onboardingViewPager.setCurrentItem(pagePosition, true);
            } else {
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.container, new MainFragment())
                            .commit();
                }
            }
        });

        imageButtonPrevious.setOnClickListener(v -> {
            if (pagePosition != 0) {
                pagePosition -= 1;
                onboardingViewPager.setCurrentItem(pagePosition, true);
            } else {
                imageButtonPrevious.setVisibility(View.INVISIBLE);
            }
        });
    }
}
