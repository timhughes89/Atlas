package com.timsimonhughes.spacex.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.timsimonhughes.appcore.views.cardStackView.CardStackLayoutManager
import com.timsimonhughes.appcore.views.cardStackView.CardStackListener
import com.timsimonhughes.appcore.views.cardStackView.config.Direction
import com.timsimonhughes.appcore.views.cardStackView.config.SwipeableMethod
import com.timsimonhughes.appcore.views.cardStackView.internal.CardStackSnapHelper
import com.timsimonhughes.spacex.databinding.FragmentLaunchesBinding
import com.timsimonhughes.spacex.model.Capsule
import com.timsimonhughes.spacex.ui.adapter.CapsuleClickListener
import com.timsimonhughes.spacex.ui.adapter.CapsulesAdapter
import com.timsimonhughes.spacex.ui.viewModel.LaunchesViewModel
import kotlinx.android.synthetic.main.fragment_launches.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class LaunchesFragment : Fragment(), CardStackListener,
    CapsuleClickListener {

    private val launchesViewModel: LaunchesViewModel by viewModel()
    private var capsuleAdapter = CapsulesAdapter(this)
    private lateinit var binding: FragmentLaunchesBinding

    private var currentPageIndex: Int = 0
    private lateinit var currentItem: Capsule
    private lateinit var itemsArray: ArrayList<Capsule>

    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = Hold().apply {
            duration = 400L
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        itemsArray = launchesViewModel.getItemsArray()

        cardStackLayoutManager = CardStackLayoutManager(requireContext(), this).apply {
            setDirections(Direction.HORIZONTAL)
            setStackFrom(CardStackLayoutManager.StackFrom.Bottom)
            setScaleInterval(0.9f)
            setMaxDegree(20.0f)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
            setSwipeThreshold(0.2f)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setCanScrollVertical(false)
        }

        binding.recyclerViewLaunches.apply {
            layoutManager = cardStackLayoutManager
            adapter = capsuleAdapter.apply {
                submitList(itemsArray)
            }
        }

        currentItem = itemsArray[0]
        binding.pageIndicatorViewLaunches.setupWithRecyclerView(cardStackLayoutManager)

//        lifecycleScope.launch {
//            launchesViewModel.indicatorCountState.collect { value ->
//                currentPageIndex = value
//            }
//        }

        binding.pageIndicatorViewLaunches.setCurrentIndex(currentPageIndex)
    }

    override fun onCapsuleClicked(sharedView: View, capsule: Capsule) {
        val direction = LaunchesFragmentDirections.actionNavigationLaunchesToLaunchesDetailFragment(
            id = capsule._id
        )
        val extras = FragmentNavigatorExtras(sharedView to "shared_element")

        navigateToLaunchesDetail(direction, extras)
    }

    private fun navigateToLaunchesDetail(direction: NavDirections, extras: FragmentNavigator.Extras) {
        findNavController().navigate(direction, extras)
    }

    override fun onCardDragging(direction: Direction, ration: Float) {
        Log.d("CARD", "onCardDragging")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CARD", "onCardSwiped")
        capsuleAdapter.removeItem(currentItem)
        itemsArray.add(currentItem)
        currentItem = itemsArray[0]
        capsuleAdapter.notifyDataSetChanged()

        if (currentPageIndex < itemsArray.size - 1)  currentPageIndex++ else currentPageIndex = 0
        binding.pageIndicatorViewLaunches.setCurrentIndex(currentPageIndex)
    }

    override fun onCardRewound() {
        Log.d("CARD", "onCardRewound")
    }

    override fun onCardCancelled() {
        Log.d("CARD", "onCardCancelled")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.d("CARD", "onCardAppeared")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        Log.d("CARD", "onCardDisappeared")
    }
}