package com.timsimonhughes.atlas.ui.fragments;

import android.content.Intent;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.timsimonhughes.atlas.ui.adapters.FragmentPagerAdapter;
import com.timsimonhughes.atlas.ui.activities.SettingsActivity;
import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.ui.activities.MainActivity;


import androidx.viewpager.widget.ViewPager;

public class MainFragment extends Fragment  {

    private View view;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_stories:
                    viewPager.setCurrentItem(0);
//                    mToolbar.setTitle(getResources().getString(R.string.fragment_1_label));
                    return true;
                case R.id.action_discover:
                    viewPager.setCurrentItem(1);
//                    mToolbar.setTitle(getResources().getString(R.string.fragment_2_label));
                    return true;
                case R.id.action_photos:
                    viewPager.setCurrentItem(2);
//                     mToolbar.setTitle(getResources().getString(R.string.fragment_3_label));
//                    return true;
            }
            return false;
        }
    };

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        viewPager = view.findViewById(R.id.view_pager);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);

        MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity != null) {
            Toolbar toolbar = view.findViewById(R.id.toolbar_list);
            mainActivity.getSupportActionBar();
            mainActivity.setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
        }

        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        FragmentPagerAdapter viewPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new StoriesFragment());
        viewPagerAdapter.addFragment(new MissionsFragment());
        viewPagerAdapter.addFragment(new PhotosFragment());
        viewPager.setAdapter(viewPagerAdapter);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        switch (menuItem) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
