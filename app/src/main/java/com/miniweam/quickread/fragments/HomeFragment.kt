package com.miniweam.quickread.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.miniweam.quickread.DummyItem.getCategories
import com.miniweam.quickread.DummyItem.getData
import com.miniweam.quickread.adapters.FeedsAdapter
import com.miniweam.quickread.adapters.FeedsCategoryAdapter
import com.miniweam.quickread.arch.FeedState
import com.miniweam.quickread.arch.FeedsViewModel
import com.miniweam.quickread.databinding.FragmentHomeBinding
import com.miniweam.quickread.util.ApiService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val categoryAdapter by lazy { FeedsCategoryAdapter() }
    private val feedsAdapter by lazy { FeedsAdapter() }
    private val viewModel by activityViewModels<FeedsViewModel>()

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
        binding.categoryRecyclerView.adapter = categoryAdapter

        categoryAdapter.submitList(getCategories())
        lifecycleScope.launch {
            viewModel.homeFirstRun.collect{
                if (it){
                    getHomeFeeds()
                }
            }
        }

        feedsAdapter.adapterClick {
            val action = HomeFragmentDirections.actionHomeFragmentToNewsSummaryFragment(it.id)
            findNavController().navigate(action)
        }



    }

    private fun getHomeFeeds(){
        viewModel.getAllFeeds()
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
                    binding.emptyStateTv.isVisible = false
                    binding.newsRecyclerView.isVisible = true
                    binding.emptyStateTv.text = state.msg
                }
            }
        }
    }}

    private fun getTestData(){
        binding.holderIcon.setOnClickListener {
            Log.d("JOE", "API RESPONSE : Loading... ")
            lifecycleScope.launch {
                try {
                    val body = ApiService.qrApiService.getAllNews()
                    Log.d("JOE", "API RESPONSE (size: ${body.body()?.data?.size}): ${body.body()} ")
                }catch(e:Exception){
                    Log.d("JOE", "API RESPONSE : ERROR $e ")
                }
            }

        }
        binding.textView.setOnClickListener {
            Log.d("JOE", "API RESPONSE : Loading..... ")
            lifecycleScope.launch {
                try {
                    val body = ApiService.qrApiService.getNews(1)
                    Log.d("JOE", "API RESPONSE (single): ${body.body()} ")
                }catch(e:Exception){
                    Log.d("JOE", "API RESPONSE : ERROR $e ")
                }
            }

        }

    }
}