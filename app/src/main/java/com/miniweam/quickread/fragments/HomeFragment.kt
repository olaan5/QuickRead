package com.miniweam.quickread.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miniweam.quickread.DummyItem.getCategories
import com.miniweam.quickread.DummyItem.getData
import com.miniweam.quickread.adapters.FeedsAdapter
import com.miniweam.quickread.adapters.FeedsCategoryAdapter
import com.miniweam.quickread.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val categoryAdapter by lazy { FeedsCategoryAdapter() }
    private val feedsAdapter by lazy { FeedsAdapter() }

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

        feedsAdapter.submitList(getData())
        categoryAdapter.submitList(getCategories())

    }
}