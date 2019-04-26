package com.timsimonhughes.atlas.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timsimonhughes.atlas.Constants;
import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.model.Planet;
import com.timsimonhughes.atlas.ui.views.PlanetOrbitView;
import com.timsimonhughes.atlas.utils.ImageUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MissionsDetailFragment extends Fragment {

    private PlanetOrbitView planetOrbitView;

    static MissionsDetailFragment newInstance(Planet planet, String transitionName) {
        MissionsDetailFragment missionsDetailFragment = new MissionsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.MISSION_ITEM, planet);
        bundle.putString(Constants.MISSION_SHARED_ITEM_STRING, transitionName);
        missionsDetailFragment.setArguments(bundle);
        return missionsDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_planet_detail, container, false);
        planetOrbitView = view.findViewById(R.id.planet_orbit_view);
        initMissionDetailUI(view);

        planetOrbitView.startLooper();
        return view;
    }

    private void initMissionDetailUI(View view) {
        ImageView imageViewMissionDetail = view.findViewById(R.id.image_view_mission_detail);
//        ImageView imageViewCustomDrawable = view.findViewById(R.id.iamge_view_custom_drawable);
        TextView textViewPlanetTitle = view.findViewById(R.id.text_view_planet_title);
        TextView textViewPlanetOverview = view.findViewById(R.id.text_view_planet_overview);
        TextView textViewPlanetDistanceSol = view.findViewById(R.id.text_view_planet_distance_sol_value);
        TextView textViewPlanetOrbitalPeriod = view.findViewById(R.id.text_view_planet_orbital_period_value);
        TextView textViewPlanetType = view.findViewById(R.id.text_view_planet_type_value);
        TextView textViewPlanetMoons = view.findViewById(R.id.text_view_planet_moon_value);


//        PolygonLapsDrawable polygonLapsDrawable = new PolygonLapsDrawable();
//        imageViewCustomDrawabel.setImageDrawable(polygonLapsDrawable);

        if (getArguments() != null) {
            Planet planet = getArguments().getParcelable(Constants.MISSION_ITEM);
            String transitionName = getArguments().getString(Constants.MISSION_SHARED_ITEM_STRING);

            if (planet != null) {
                String planetImageUrl = planet.getImage_url();
                String planetTitle = planet.getTitle();
                String planetOverview = planet.getOverview();
                String planetDistanceSol = planet.getDistance_sol();
                String planetOrbitalPeriod = planet.getOrbital_period();
                String planetType = planet.getPlanet_type();
                String planetMoons = planet.getMoons();

                imageViewMissionDetail.setTransitionName(transitionName);
                ImageUtils.loadImage(getContext(), planetImageUrl, imageViewMissionDetail);

                textViewPlanetTitle.setText(planetTitle);
                textViewPlanetOverview.setText(planetOverview);
                textViewPlanetDistanceSol.setText(planetDistanceSol);
                textViewPlanetOrbitalPeriod.setText(planetOrbitalPeriod);
                textViewPlanetType.setText(planetType);
                textViewPlanetMoons.setText(planetMoons);

                AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                if (appCompatActivity != null) {
                    Toolbar missionToolbar = view.findViewById(R.id.toolbar_mission_detail);
                    if (appCompatActivity.getSupportActionBar() != null) {
                        appCompatActivity.setSupportActionBar(missionToolbar);
                        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(false);
                        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    }

                    missionToolbar.setNavigationOnClickListener(v -> {
                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    });

                    missionToolbar.setTitle("");
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop the animation loop if going into background
        planetOrbitView.stopLooper();
    }

    @Override
    public void onResume() {
        // Resume the animation
        super.onResume();
        planetOrbitView.stopLooper();
    }
}
