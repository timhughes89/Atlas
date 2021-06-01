package com.timsimonhughes.discover.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.timsimonhughes.discover.databinding.FragmentDiscoverBinding
import com.timsimonhughes.discover.model.Planet
import com.timsimonhughes.discover.ui.adapter.DiscoverCardAdapter
import com.timsimonhughes.discover.ui.adapter.DiscoverClickListener
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class DiscoverFragment : Fragment(), CardStackListener, DiscoverClickListener {

    private val discoverViewModel: DiscoverViewModel by viewModel()
    private var discoverAdapter = DiscoverCardAdapter(this)
    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var planetArray: ArrayList<Planet>
    private lateinit var lastItem: Planet
    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        planetArray = discoverViewModel.getPlanetsArray()

        // Defining the shared enter & return materialContainerTransform transitions.
        val materialContainerTransform = MaterialContainerTransform().apply {
            duration = 400L
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            scrimColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
            interpolator = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in)
        }

        sharedElementEnterTransition = materialContainerTransform
        sharedElementReturnTransition = materialContainerTransform
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        cardStackLayoutManager = CardStackLayoutManager(context).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
            setStackFrom(StackFrom.Right)
            setVisibleCount(3)
            setDirections(Direction.HORIZONTAL)
            setCanScrollVertical(false)
        }

        cardStackView.apply {
            layoutManager = cardStackLayoutManager
            adapter = discoverAdapter.apply {
                submitList(planetArray)
                lastItem = planetArray[0]
                stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
            }
        }

        binding.previousButton.setOnClickListener {

        }

        binding.nextButton.setOnClickListener {
            cardStackView.smoothScrollToPosition(1)
        }
    }


    override fun onItemClick(sharedView: View, planetItem: Planet) {
        val direction = DiscoverFragmentDirections.actionNavigationDiscoverToNavigationDiscoverDetail(
            itemId = planetItem.id
        )
        val extras = FragmentNavigatorExtras(sharedView to "discover_to_discover_detail")
        navigateToDiscoverDetail(direction, extras)
    }

    private fun navigateToDiscoverDetail(direction: NavDirections, extras: FragmentNavigator.Extras) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 300L
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 300L
        }

        findNavController().navigate(direction, extras)
    }


    override fun onCardDisappeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun onCardSwiped(direction: Direction?) {
        discoverAdapter.removeItem(lastItem)
        planetArray.add(lastItem)
        lastItem = planetArray[0]
        discoverAdapter.notifyDataSetChanged()
    }

    override fun onCardCanceled() {
        TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardRewound() {
        TODO("Not yet implemented")
    }


}