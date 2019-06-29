package com.timsimonhughes.atlas.ui.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timsimonhughes.atlas.R;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class OnboardingPagerAdapter extends PagerAdapter {

    private Context context;
    private TypedArray fragmentImages;
    private String[] fragmentTitles;
    private String[] fragmentDescriptions;

    public OnboardingPagerAdapter(Context context) {
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        this.fragmentImages = context.getResources().obtainTypedArray(R.array.onboarding_fragment_images);
        this.fragmentTitles = context.getResources().getStringArray(R.array.onboarding_fragment_titles);
        this.fragmentDescriptions = context.getResources().getStringArray(R.array.onboarding_fragment_descriptions);
    }

    @Override
    public int getCount() {
        return fragmentTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public Object instantiateItem(@NotNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_onboarding_layout, container, false);

        ImageView imageViewOnboarding = view.findViewById(R.id.image_view_onboarding);
        TextView textViewOnboardingTitle = view.findViewById(R.id.text_view_onboarding_title);
        TextView textViewOnboardingDescription = view.findViewById(R.id.text_view_onboarding_description);

        imageViewOnboarding.setImageResource(fragmentImages.getResourceId(position, 0));
        textViewOnboardingTitle.setText(fragmentTitles[position]);
        textViewOnboardingDescription.setText(fragmentDescriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
