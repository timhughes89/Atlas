package com.timsimonhughes.news.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.transition.Hold
import com.timsimonhughes.news.R
import com.timsimonhughes.news.databinding.FragmentNewsBinding
import com.timsimonhughes.news.model.News
import com.timsimonhughes.news.model.NewsType
import com.timsimonhughes.news.ui.adapter.NewsAdapter
import com.timsimonhughes.news.ui.viewModel.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class NewsFragment : Fragment(), NewsAdapter.NewsItemClickListener {

    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter = NewsAdapter(this)
    private lateinit var binding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

//        enterTransition = MaterialFadeThrough().apply {
//            duration = 300L
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        observeViewModel()

        binding.contentNewsMain.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        }

        val check = (binding.contentNewsMain.chipGroup.getChildAt(0) as Chip).apply {
            isChecked = true
        }
        updateFilter(check.id)

        binding.contentNewsMain.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            updateFilter(checkedId)
        }
    }

    private fun observeViewModel() {
        newsViewModel.newsData.observe(viewLifecycleOwner, Observer {
            newsAdapter.submitList(it)
        })
    }

    private fun updateFilter(checkedId: Int) {
        val type = when (checkedId) {
            R.id.chipHumansInSpace -> NewsType.HUMANS
            R.id.chipMoonToMars -> NewsType.MOON
            R.id.chipEarth -> NewsType.EARTH
            R.id.chipSpaceTech -> NewsType.SPACE_TECH
            R.id.chipFlight -> NewsType.FLIGHT
            R.id.chipSolar -> NewsType.SOLAR
            R.id.chipStem -> NewsType.STEM
            R.id.chipHistory -> NewsType.HISTORY
            R.id.chipBenefits -> NewsType.BENEFITS
            else -> NewsType.ALL
        }
        newsViewModel.filterItems(type)
    }

    override fun onItemClick(sharedView: View, newsItem: News) {
        val hold = Hold().apply {
            duration = 300L
        }
        hold.addTarget(binding.newsParent)
        exitTransition = hold

        val directions = NewsFragmentDirections.actionNavigationNewsToNavigationNewsDetail(
            itemId = newsItem.id
        )
        val extras = FragmentNavigatorExtras(sharedView to "news_to_news_detail")

        findNavController().navigate(directions, extras)
    }



}