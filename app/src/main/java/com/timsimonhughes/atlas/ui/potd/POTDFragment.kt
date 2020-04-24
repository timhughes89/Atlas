package com.timsimonhughes.atlas.ui.potd

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timsimonhughes.atlas.Constants
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.model.POTD
import com.timsimonhughes.atlas.network.ApiConfig
import com.timsimonhughes.atlas.network.NasaPotdService
import com.timsimonhughes.atlas.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class POTDFragment : Fragment(),
    POTDItemClickListener {

    private val MOVE_DEFAULT_TIME: Long = 1000
    private val FADE_DEFAULT_TIME: Long = 300
    private val cacheSize = 10 * 1024 * 1024

//    private val TAG = MainFragment::class.java.simpleName

    private lateinit var photoOfDay: POTD
    private lateinit var POTDList: List<POTD>
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var endDate: String
    private lateinit var startDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)
        initUI(view)
        getDateRange()
        fetchPhotoOfDay()
        return view
    }

    private fun initUI(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view_potd)
        recyclerView.layoutManager = LinearLayoutManager(context)
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
        val service = RetrofitClientInstance().getRetrofitInstance(context, cacheSize).create(NasaPotdService::class.java)
        val dateRangeCall = service.getPOTDByDateRange("2019-04-01", "2019-04-12", ApiConfig.API_KEY)
        dateRangeCall.enqueue(object : Callback<List<POTD>> {
            override fun onResponse(call: Call<List<POTD>>, response: Response<List<POTD>>) {
                if (response.body() != null) {
                    POTDList = response.body() as ArrayList
                    progressBar.visibility = View.GONE
                    updateAdapter(POTDList)
                }
            }

            override fun onFailure(call: Call<List<POTD>>, t: Throwable) {
//                Log.d(TAG, "TAG Message")
            }
        })
    }

    private fun updateAdapter(photosOfTheDayRange: List<POTD>) {

        val layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        val potdAdapter = POTDAdapter(context, photosOfTheDayRange)
        potdAdapter.setOnItemClickListener(this)
        recyclerView.adapter = potdAdapter
        recyclerView.layoutAnimation = layoutAnimationController
        recyclerView.scheduleLayoutAnimation()
        potdAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int, sharedView: View?, potd: POTD?) {
        val fragmentManager = fragmentManager
        val transitionName = sharedView?.transitionName

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addSharedElement(sharedView!!, transitionName!!)
        fragmentTransaction.commit()
    }
}