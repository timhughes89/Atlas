package com.timsimonhughes.spacex.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.timsimonhughes.spacex.databinding.LaunchesDetailFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LaunchesDetailFragment : Fragment() {

    private val args: LaunchesDetailFragmentArgs by navArgs()
    private val launchesDetailViewModel: LaunchesDetailViewModel by viewModel()

    private lateinit var binding: LaunchesDetailFragmentBinding

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
        binding = LaunchesDetailFragmentBinding.inflate(inflater, container, false).apply {
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchesDetailViewModel.getItemById(args.id)?.let {
            binding.capsule = it
        }
    }
}