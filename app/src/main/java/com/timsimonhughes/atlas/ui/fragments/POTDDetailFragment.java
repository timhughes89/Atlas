package com.timsimonhughes.atlas.ui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.timsimonhughes.atlas.Constants;
import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.model.POTD;
import com.timsimonhughes.atlas.utils.ImageUtils;

public class POTDDetailFragment extends Fragment {

    private View view;
    private String title, imageUrl, explanation, transitionName;
    private ImageView imageView;
    private TextView textViewTitle, textViewExplanation;
    private NestedScrollView nestedScrollView;

    public POTDDetailFragment() {
    }

    public static POTDDetailFragment newInstance(POTD potd, String transitionName) {
        POTDDetailFragment potdFragment = new POTDDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.POTD_ITEM, potd);
        bundle.putString(Constants.SHARED_ITEM_VIEW, transitionName);
        potdFragment.setArguments(bundle);
        return potdFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_potd_detail, container, false);

        if (getArguments() != null) {
            POTD potd = getArguments().getParcelable(Constants.POTD_ITEM);
            transitionName = getArguments().getString(Constants.SHARED_ITEM_VIEW);

            if (potd != null) {
                title = potd.getTitle();
                imageUrl = potd.getUrl();
                explanation = potd.getExplanation();
            }
        }

        initUI();
        initToolbar();
        delayCardAnimation();

        imageView.setTransitionName(transitionName);
        ImageUtils.loadImage(getContext(), imageUrl, imageView);
        textViewTitle.setText(title);
        textViewExplanation.setText(explanation);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        startPostponedEnterTransition();
    }

    private void initUI() {
        imageView = view.findViewById(R.id.image_view_potd);
        nestedScrollView = view.findViewById(R.id.nested_scroll_view_content);
        textViewTitle = view.findViewById(R.id.text_view_potd_title);
        textViewExplanation = view.findViewById(R.id.text_view_potd_explanation);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(););
    }

        private void initToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            Toolbar detailToolbar = view.findViewById(R.id.toolbar_detail);
            if (appCompatActivity.getSupportActionBar() != null) {
                appCompatActivity.setSupportActionBar(detailToolbar);
                appCompatActivity.getSupportActionBar().setHomeButtonEnabled(false);
                appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            }
            detailToolbar.setNavigationOnClickListener(v -> {
                if (getFragmentManager()!= null) {
                    getFragmentManager().popBackStack();
                }
            });
            detailToolbar.setTitle(title);
        }
    }

    private void delayCardAnimation() {
        final int startScrollPos = getResources().getDimensionPixelOffset(R.dimen.scroll_distance);
        new Handler().postDelayed(() -> {
            Animator animator = ObjectAnimator.ofInt(nestedScrollView, Constants.SCROLL_PROPERTY_NAME, startScrollPos)
                    .setDuration(500);
            animator.start();
        }, 300);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_favourite:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}