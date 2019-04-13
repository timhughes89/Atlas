package com.timsimonhughes.atlas.ui.fragments

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
import com.timsimonhughes.atlas.ui.OnItemClickListener
import com.timsimonhughes.atlas.ui.adapters.POTDAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PhotoFragment : Fragment(), OnItemClickListener {


    private val MOVE_DEFAULT_TIME: Long = 1000
    private val FADE_DEFAULT_TIME: Long = 300

    private val TAG = MainFragment::class.java.simpleName

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
        val view = inflater.inflate(R.layout.frag_photos, container, false)
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
        val service = RetrofitClientInstance.getRetrofitInstance().create(NasaPotdService::class.java)
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
                Log.d(TAG, "TAG Message")
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
//        val potdFragment = POTDDetailFragment().newInstance(potd, transitionName)

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addSharedElement(sharedView!!, transitionName!!)
//        fragmentTransaction.replace(R.id.container, potdFragment)
        fragmentTransaction.commit()


    }

//    override fun onItemClick(position: Int, sharedView: View, potd: POTD) {
//
//        val fragmentManager = fragmentManager
//
//        if (fragmentManager != null) {
//            val transitionName = sharedView.transitionName
//
//            val previousFragment = fragmentManager.findFragmentById(R.id.container)
//            val potdFragment = POTDDetailFragment().newInstance(potd, transitionName)
//
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.setReorderingAllowed(true)
//            fragmentTransaction.addSharedElement(sharedView, transitionName)
//            //            fragmentTransaction.setTransition(getResources().get);
//            fragmentTransaction.addToBackStack("")
//            fragmentTransaction.replace(R.id.container, potdFragment)
//            fragmentTransaction.commit()
//            //
//            //            // 1. Exit for Previous Fragment
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
//        }
//    }

    //        Call<POTD> dateCall = service.getPOTDByDate("2019-02-02", ApiConfig.API_KEY);
//        dateCall.enqueue(new Callback<POTD>() {
//            @Override
//            public void onResponse(Call<POTD> call, Response<POTD> response) {
//                if (response.body() != null) {
//                    photoOfDay = response.body();
//                    progressBar.setVisibility(View.GONE);
//                    updateAdapter(photoOfDay);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<POTD> call, Throwable t) {
//
//            }
//        });


//        Call<POTD> call = service.getPhotoOfDay(ApiConfig.API_KEY);
//        call.enqueue(new Callback<POTD>() {
//            @Override
//            public void onResponse(Call<POTD> call, Response<POTD> response) {
//                if (response.body() != null) {
//                    photoOfDay = response.body();
//                    progressBar.setVisibility(View.GONE);
//                    updateAdapter(photoOfDay);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<POTD> call, Throwable t) {
//            }
//        });

//    private void updateAdapter(POTD potd) {
//        POTDList.add(potd);
//        POTDAdapter potdAdapter = new POTDAdapter(this, POTDList);
//        recyclerView.setAdapter(potdAdapter);
//        potdAdapter.notifyDataSetChanged();
//    }
}