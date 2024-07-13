package com.miniweam.quickread.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.miniweam.quickread.DummyItem.getCategories
import com.miniweam.quickread.adapters.FeedsAdapter
import com.miniweam.quickread.adapters.FeedsCategoryAdapter
import com.miniweam.quickread.arch.FeedState
import com.miniweam.quickread.arch.FeedsViewModel
import com.miniweam.quickread.arch.FeedsViewModelFactory
import com.miniweam.quickread.databinding.FragmentHomeBinding
import com.miniweam.quickread.db.QrDatabase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val categoryAdapter by lazy { FeedsCategoryAdapter() }
    private val feedsAdapter by lazy { FeedsAdapter() }
    private val viewModel by activityViewModels<FeedsViewModel>{FeedsViewModelFactory(QrDatabase.getDatabase(requireContext()))}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsRecyclerView.adapter = feedsAdapter

        categoryAdapter.submitList(getCategories())
        lifecycleScope.launch {
            viewModel.homeFirstRun.collect{
                if (it){
                    viewModel.getAllFeeds()
                    viewModel.toggleHomeFirstRun(false)
                }
            }
        }
        getHomeFeeds()
        feedsAdapter.adapterClick {
            val action = HomeFragmentDirections.actionHomeFragmentToNewsSummaryFragment(it.id)
            findNavController().navigate(action)
        }



    }

    private fun getHomeFeeds(){
        lifecycleScope.launch {
        viewModel.allHomeFeedsFlow.collect{state->
            when(state){
                is FeedState.Loading->{
                    binding.progressBar.isVisible = true
                    binding.emptyStateTv.isVisible = false
                    binding.newsRecyclerView.isVisible = false
                }
                is FeedState.Successful->{
                    binding.progressBar.isVisible = false
                    binding.emptyStateTv.isVisible = false
                    binding.newsRecyclerView.isVisible = true
                    feedsAdapter.submitList(state.allResponseBody.data)
                }
                is FeedState.Failure->{
                    binding.progressBar.isVisible = false
                    binding.emptyStateTv.isVisible = true
                    binding.newsRecyclerView.isVisible = false
                    binding.emptyStateTv.text = state.msg
                }
            }
        }
    }}

}