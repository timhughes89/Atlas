package com.timsimonhughes.atlas.ui.adapters;

import android.content.Context;
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

    private LayoutInflater layoutInflater;
    private Context context;

    public int[] fragmentImages = {R.drawable.example_image, R.drawable.example_image, R.drawable.example_image};
    private String[] fragmentTitles = {"All the latest space related news", "Discover something new", "A new photo every day"};
    private String[] fragmentDescriptions = {"Description 1", "Description 2", "Description 3"};

    public OnboardingPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return fragmentTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    public Object instantiateItem(@NotNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_onboarding_layout, container, false);

        ImageView imageViewOnboarding = view.findViewById(R.id.image_view_onboarding);
        TextView textViewOnboardingTitle = view.findViewById(R.id.text_view_onboarding_title);
        TextView textViewOnboardingDescription = view.findViewById(R.id.text_view_onboarding_description);

        imageViewOnboarding.setImageResource(fragmentImages[position]);
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
