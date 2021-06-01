package com.timsimonhughes.news.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.timsimonhughes.appcore.utils.loadListener
import com.timsimonhughes.news.R
import com.timsimonhughes.news.databinding.FragmentNewsDetailBinding
import com.timsimonhughes.news.ui.viewModel.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {

    private val newsViewModel: NewsViewModel by viewModel()
    private lateinit var binding: FragmentNewsDetailBinding

    val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Defining the shared enter & return materialContainerTransform transitions.
        val materialContainerTransform = MaterialContainerTransform().apply {
            duration = 300L
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            scrimColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
            interpolator = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in)
        }

        sharedElementEnterTransition = materialContainerTransform
        sharedElementReturnTransition = materialContainerTransform
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newsItem = newsViewModel.getItem(args.itemId)

        binding = FragmentNewsDetailBinding.inflate(inflater, container, false).apply {
            this.item = newsItem

            imageLoadListener = loadListener {
                startPostponedEnterTransition()
            }

            this.toolbarNewsDetail.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}
