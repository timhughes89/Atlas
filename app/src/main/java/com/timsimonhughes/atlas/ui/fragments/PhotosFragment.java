package com.timsimonhughes.atlas.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.timsimonhughes.atlas.network.ApiConfig;
import com.timsimonhughes.atlas.network.RetrofitClientInstance;
import com.timsimonhughes.atlas.Constants;
import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.model.POTD;
import com.timsimonhughes.atlas.network.NasaPotdService;
import com.timsimonhughes.atlas.ui.listeners.POTDOnItemClickListener;
import com.timsimonhughes.atlas.ui.adapters.POTDAdapter;
import com.timsimonhughes.atlas.ui.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosFragment extends Fragment implements POTDOnItemClickListener {

    private List<POTD> POTDList = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView recyclerview;
    private String endDate, startDate;
    private View view;

    public PhotosFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_photos, container, false);
        initUI(view);
        getDateRange();
        fetchPhotoOfDay();
        return view;
    }

    private void initUI(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerview = view.findViewById(R.id.recycler_view_potd);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getDateRange() {
        SimpleDateFormat utcFormatter = new SimpleDateFormat(Constants.UTC_FORMAT, Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -4);

        endDate = utcFormatter.format(currentDate);
        startDate = utcFormatter.format(calendar.getTime());
    }

    private void fetchPhotoOfDay() {
        NasaPotdService nasaPotdService = RetrofitClientInstance.getRetrofitInstance().create(NasaPotdService.class);
        Call<List<POTD>> call = nasaPotdService.getPOTDByDateRange(startDate, endDate, ApiConfig.API_KEY);
        call.enqueue(new Callback<List<POTD>>() {
            @Override
            public void onResponse(Call<List<POTD>> call, Response<List<POTD>> response) {
                if (response.body() != null) {
                    POTDList = response.body();
                    progressBar.setVisibility(View.GONE);
                    updateAdapter(POTDList);
                }
            }

            @Override
            public void onFailure(Call<List<POTD>> call, Throwable t) {
                showErrorSnackBar();
            }
        });
    }

    private void showErrorSnackBar() {
        Snackbar.make(view,"There was an network error", Snackbar.LENGTH_LONG).show();
    }

    private void updateAdapter(List<POTD> potdList) {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);

        POTDAdapter potdAdapter = new POTDAdapter(getContext(), potdList);
        potdAdapter.setOnItemClickListener(this);
        recyclerview.setAdapter(potdAdapter);
        recyclerview.setLayoutAnimation(layoutAnimationController);
        recyclerview.scheduleLayoutAnimation();
        potdAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, View sharedView, POTD potd) {

        if (getFragmentManager() != null) {

            MainActivity mainActivity = (MainActivity) getActivity();

            if (mainActivity != null)  {
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

                String transitionName = sharedView.getTransitionName();
                POTDDetailFragment potdDetailFragment = POTDDetailFragment.newInstance(potd, transitionName);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addSharedElement(sharedView, transitionName);
                fragmentTransaction.replace(R.id.container, potdDetailFragment);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
            }
        }

//        if (getParentFragment() != null) {
//            fragmentManager = getParentFragment().getChildFragmentManager();
//
//            String transitionName = sharedView.getTransitionName();
//            POTDDetailFragment potdDetailFragment = POTDDetailFragment.newInstance(potd, transitionName);
//
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.setReorderingAllowed(true);
//            fragmentTransaction.addSharedElement(sharedView, transitionName);
//            fragmentTransaction.replace(R.id.container, potdDetailFragment);
//            fragmentTransaction.commit();
//        }

    }
}
