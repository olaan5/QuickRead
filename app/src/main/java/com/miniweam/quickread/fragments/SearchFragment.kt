package com.miniweam.quickread.fragments

import android.R
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.miniweam.quickread.adapters.FeedsSearchAdapter
import com.miniweam.quickread.arch.FeedState
import com.miniweam.quickread.arch.FeedsViewModel
import com.miniweam.quickread.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val feedsAdapter by lazy { FeedsSearchAdapter() }
    private val viewModel by activityViewModels<FeedsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsRecyclerView.adapter = feedsAdapter
//        feedsAdapter.submitList(DummyItem.getData())

        // TODO: Add a adapter for recent search then add the logic to alternate between the two recycler views
        changeSearchViewPlate()
        setUpQueryListener()
        getSearchedFeeds()
        feedsAdapter.adapterClick {
            val action = SearchFragmentDirections.actionAccountFragmentToNewsSummaryFragment(it.id)
            findNavController().navigate(action)
        }


    }

    private fun getSearchedFeeds() {
        lifecycleScope.launch {
            viewModel.searchFeedFlow.collect { state ->
                when (state) {
                    is FeedState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.emptyStateTv.isVisible = false
                        binding.newsRecyclerView.isVisible = false
                    }
                    is FeedState.Successful -> {
                        binding.progressBar.isVisible = false
                        binding.emptyStateTv.isVisible = false
                        binding.newsRecyclerView.isVisible = true
                        feedsAdapter.submitList(state.allResponseBody.data)
                    }
                    is FeedState.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.emptyStateTv.isVisible = true
                        binding.newsRecyclerView.isVisible = false
                        binding.emptyStateTv.text = state.msg
                    }
                }

            }
        }
    }

    private fun setUpQueryListener() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchFeed(query.trim())
                    Log.d("search query", "itemQuery")
                } ?: viewModel.toggleSearchFeedState(FeedState.Failure("Search Feeds..."))
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    if (newText.trim().isEmpty()) {
                        viewModel.toggleSearchFeedState(FeedState.Failure("Search Feeds..."))
                        Log.d("search query", "EMPTY")
                        return@let
                    }
                    viewModel.searchFeed(newText.trim())
                    Log.d("search query", "itemQuery")

                } ?: viewModel.toggleSearchFeedState(FeedState.Failure("Search Feeds..."))
                return true
            }

        })
    }


    private fun changeSearchViewPlate(){
        val searchPlate = binding.searchBar.findViewById<View>(androidx.appcompat.R.id.search_plate)
        searchPlate.setBackgroundResource(com.miniweam.quickread.R.drawable.transparent_background)
    }
}