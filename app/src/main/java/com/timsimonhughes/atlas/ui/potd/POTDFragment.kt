package com.timsimonhughes.atlas.ui.potd

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.timsimonhughes.atlas.Constants
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.ui.MainActivity
import com.timsimonhughes.atlas.ui.potd.adapter.POTDAdapter
import com.timsimonhughes.atlas.ui.potd.viewModel.POTDViewModel
import kotlinx.android.synthetic.main.fragment_photos.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.timsimonhughes.atlas.model.potd.POTDResult
//import kotlinx.android.synthetic.main.content_news_error.*
import kotlinx.android.synthetic.main.content_potd.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class POTDFragment : Fragment(R.layout.fragment_photos) {

    private val potdViewModel: POTDViewModel by viewModel()

    private val potdAdapter = POTDAdapter()
    private lateinit var endDate: String
    private lateinit var startDate: String

    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setState(UIState.LOADING)
        initialiseAdapter()
        fetchPhotoOfDay()

        fabPotd.setOnClickListener {
            createDatePicker(requireContext())
        }

//        btn_feed_error_button.setOnClickListener {
//            setState(UIState.LOADING)
//            fetchPhotoOfDay()
//        }
    }

    private fun createDatePicker(context: Context) {
        val calendarConstraintBuilder = CalendarConstraints.Builder()
        val supportFragmentManager = (context as MainActivity).supportFragmentManager
        val materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Pick a date range")
            .setTheme(R.style.ThemeOverlay_Atlas_MaterialCalendar)
            .build()

        materialDatePickerBuilder.show(supportFragmentManager, "TAG")

    }

    private fun initialiseAdapter() {
        recyclerViewPotd.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = potdAdapter
        }
    }

    private fun getDateRange() {
        val utcFormatter = SimpleDateFormat(Constants.UTC_FORMAT, Locale.getDefault())

        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        calendar.time = currentDate
        calendar.add(Calendar.DATE, -4)

        endDate = utcFormatter.format(currentDate)
        startDate = utcFormatter.format(calendar.time)
    }

    private fun fetchPhotoOfDay() {
        getDateRange()

        searchJob?.cancel()
//        searchJob = lifecycleScope.launch {
//            potdViewModel.getPOTDList("2019-04-01", "2019-04-12").observe(viewLifecycleOwner, Observer { result ->
//                potdAdapter.submitList(result)
//            })
//
//            potdViewModel.initialNetworkState().observe(viewLifecycleOwner, Observer {
//
//            })
//
//            potdViewModel.currentNetworkState().observe(viewLifecycleOwner, Observer {
//
//            })
//        }

        searchJob = lifecycleScope.launch {
            potdViewModel.getPOTDList("2019-04-01", "2019-04-12").observe(viewLifecycleOwner, Observer { result ->
                potdAdapter.submitList(result)
                setState(UIState.LOADED)
            })
        }

    }

    private fun setState(uiState: UIState) {
        when (uiState) {
            UIState.LOADING -> {
                content_potd_skeleton.visibility = View.VISIBLE
                content_potd.visibility = View.GONE
                content_news_error.visibility = View.GONE
            }
            UIState.LOADED -> {
                content_potd_skeleton.visibility = View.GONE
                content_potd.visibility = View.VISIBLE
                content_news_error.visibility = View.GONE
            }
            UIState.ERROR -> {
                content_potd_skeleton.visibility = View.GONE
                content_potd.visibility = View.GONE
                content_news_error.visibility = View.VISIBLE
            }
        }
    }

    enum class UIState {
        LOADING,
        LOADED,
        ERROR
    }
}