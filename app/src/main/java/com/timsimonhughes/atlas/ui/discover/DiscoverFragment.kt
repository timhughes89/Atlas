package com.timsimonhughes.atlas.ui.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.ui.discover.adapter.DiscoverAdapter
import com.timsimonhughes.atlas.ui.discover.viewModel.DiscoverViewModel
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private lateinit var disoverViewModel: DiscoverViewModel
    private var discoverAdapter = DiscoverAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)

        initAdapter()

        disoverViewModel.missionData.observe(viewLifecycleOwner, Observer { planetList ->
            discoverAdapter.submitList(planetList)
        })
    }

    private fun initAdapter() {
        recycler_view_planets.layoutManager = LinearLayoutManager(context)
        recycler_view_planets.adapter = discoverAdapter
    }
}