package com.timsimonhughes.atlas.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timsimonhughes.atlas.Constants;
import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.model.Planet;
import com.timsimonhughes.atlas.ui.ItemOffsetDecoration;
import com.timsimonhughes.atlas.ui.listeners.PlanetItemClickListener;
import com.timsimonhughes.atlas.ui.adapters.PlanetAdapter;
import com.timsimonhughes.atlas.ui.activities.MainActivity;

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

public class MissionsFragment extends Fragment implements PlanetItemClickListener {

    private static final String TAG = MissionsFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<Planet> planetList = new ArrayList<>();
    private PlanetAdapter missionsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_missions, container, false);
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
        Gson gson = new Gson();
        Type planetListType = new TypeToken<ArrayList<Planet>>(){}.getType();
        planetList = gson.fromJson(jsonString, planetListType);
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
        missionsAdapter = new PlanetAdapter(planetList, getContext());
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

                // Get the previous fragment by the id
                Fragment previousFragment = fragmentManager.findFragmentById(R.id.container);

                // Get an instance of the new missionDetailFragment
                Fragment missionsDetailFragment = MissionsDetailFragment.newInstance(planet, transitionName);

                // Defines enter transition for all fragment views
//                Slide slideTransition = new Slide(Gravity.RIGHT);
//                slideTransition.setDuration(1000);
//                Explode explodeTransition = new Explode();
//                explodeTransition.setDuration(500);

//                // 1. Exit for previous fragment
//                Fade exitFade = new Fade();
//                exitFade.setDuration(300);
//                if (previousFragment != null) {
//                    previousFragment.setExitTransition(exitFade);
//                }
//
//                // 2. Shared elements transition
//                TransitionSet enterTransitionSet = new TransitionSet();
//                enterTransitionSet.addTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
//                enterTransitionSet.setDuration(300);
//                enterTransitionSet.setStartDelay(300);
//                missionsDetailFragment.setSharedElementEnterTransition(enterTransitionSet);
//
//                // 3. Enter for new fragment
//                Fade enterFade = new Fade();
//                //enterFade.setStartDelay(300 + 300);
//                enterFade.setDuration(300);
//                missionsDetailFragment.setEnterTransition(enterFade);
//
//                // 4. Defines enter transition only for shared element
//                ChangeBounds changeBoundsTransition = new ChangeBounds();
//                changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
//
//                // 5. Prevent transitions for overlapping
//                missionsDetailFragment.setAllowEnterTransitionOverlap(false);
//                missionsDetailFragment.setAllowReturnTransitionOverlap(false);
//                missionsDetailFragment.setSharedElementEnterTransition(changeBoundsTransition);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addSharedElement(sharedView, transitionName);
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.replace(R.id.container, missionsDetailFragment);
                fragmentTransaction.commit();


                Log.d(TAG, transitionName);
            }
        }
    }
}

//            // 1. Exit for Previous Fragment
//            //            Fade exitFade = new Fade();
//            //            exitFade.setDuration(FADE_DEFAULT_TIME);
//            //            previousFragment.setExitTransition(exitFade);
//            //
//            //            // 2. Shared Elements Transition
//            //            TransitionSet enterTransitionSet = new TransitionSet();
//            //            enterTransitionSet.addTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
//            //            enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
//            //            enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
//            //            potdFragment.setSharedElementEnterTransition(enterTransitionSet);
//            //
//            //            // 3. Enter Transition for New Fragment
//            //            Fade enterFade = new Fade();
//            //            enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
//            //            enterFade.setDuration(FADE_DEFAULT_TIME);
//            //            potdFragment.setEnterTransition(enterFade);
//            //
//            //            fragmentTransaction.addSharedElement(sharedView, sharedView.getTransitionName());
//            //            fragmentTransaction.addToBackStack("backstack");
//            //            fragmentTransaction.replace(R.id.container, potdFragment);
//            //            fragmentTransaction.commitAllowingStateLoss();
//
//            //            fragmentManager.beginTransaction()
//            //                    .addSharedElement(sharedView, transitionName)
//            //                    .setTransition(android.R.transition.explode)
//            //                    .addToBackStack("MAIN")
//            //                    .replace(R.id.container, potdFragment)
//            //                    .commit();
