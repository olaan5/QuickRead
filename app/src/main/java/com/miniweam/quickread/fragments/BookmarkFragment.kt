package com.miniweam.quickread.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miniweam.quickread.DummyItem
import com.miniweam.quickread.adapters.FeedsAdapter
import com.miniweam.quickread.databinding.FragmentBookmarkBinding

class
BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val feedsAdapter by lazy { FeedsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsRecyclerView.adapter = feedsAdapter

        feedsAdapter.submitList(DummyItem.getData())
    }
}