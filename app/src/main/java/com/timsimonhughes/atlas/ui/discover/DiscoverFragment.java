package com.timsimonhughes.atlas.ui.discover;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;


import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.timsimonhughes.atlas.Constants;
import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.model.Planet;
import com.timsimonhughes.atlas.ui.ItemOffsetDecoration;
import com.timsimonhughes.atlas.ui.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiscoverFragment extends Fragment implements DisoverItemClickListener {

    private static final String TAG = DiscoverFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<Planet> planetList = new ArrayList<>();
    private DiscoverAdapter missionsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (planetList.isEmpty()) {
            getMissionData();
        }

        initMissionsUI(view);
    }

    private void getMissionData() {
        String jsonString = getStringJsonFromRaw();
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Planet.class);
        JsonAdapter<List<Planet>> adapter = moshi.adapter(type);
        try {
            planetList = adapter.fromJson(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStringJsonFromRaw() {
        String rawJson = null;

        try {
            if (getActivity() != null) {
                InputStream inputStream = getActivity().getResources().openRawResource(R.raw.planetary_bodies);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                rawJson = new String(buffer, Constants.CHARSET);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return rawJson;
    }

    private void initMissionsUI(View view) {

        int spacing = getContext().getResources().getDimensionPixelSize(R.dimen.unit_x1);
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);

        recyclerView = view.findViewById(R.id.recycler_view_planets);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
        missionsAdapter = new DiscoverAdapter(planetList, getContext());
        missionsAdapter.setPlanetItemClickListener(this);
        recyclerView.setAdapter(missionsAdapter);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.scheduleLayoutAnimation();
        missionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, View sharedView, Planet planet) {

        if (getFragmentManager() != null) {

            MainActivity mainActivity = (MainActivity) getActivity();

            if (mainActivity != null) {
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

                String transitionName = sharedView.getTransitionName();
                Fragment previousFragment = fragmentManager.findFragmentById(R.id.container);
                Fragment missionsDetailFragment = DiscoverDetailFragment.newInstance(planet, transitionName);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addSharedElement(sharedView, transitionName);
                fragmentTransaction.setCustomAnimations(R.anim.view_animation_fade_in, R.anim.view_animation_fade_out, R.anim.view_animation_fade_in, R.anim.view_animation_fade_out);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.replace(R.id.container, missionsDetailFragment);
                fragmentTransaction.commit();


                Log.d(TAG, transitionName);
            }
        }
    }
}


