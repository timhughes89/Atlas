package com.timsimonhughes.atlas.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timsimonhughes.atlas.R;
import com.timsimonhughes.atlas.ui.ItemOffsetDecoration;
import com.timsimonhughes.atlas.ui.adapters.NewsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment {

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_stories, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_stories);
        NewsAdapter newsAdapter = new NewsAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new ItemOffsetDecoration(16));
        recyclerView.setAdapter(newsAdapter);
    }
}
