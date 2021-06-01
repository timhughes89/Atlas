package com.timsimonhughes.discover.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.timsimonhughes.discover.databinding.FragmentDiscoverDetailBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class DiscoverDetailFragment : Fragment() {

    private val discoverViewModel: DiscoverViewModel by viewModel()
    private lateinit var binding: FragmentDiscoverDetailBinding

    private val args: DiscoverDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val item = discoverViewModel.getPlanetData(args.itemId)
        binding = FragmentDiscoverDetailBinding.inflate(inflater, container, false).apply {
            this.item = item
            this.toolbarDiscoverDetail.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}