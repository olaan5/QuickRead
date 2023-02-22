package com.miniweam.quickread

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miniweam.quickread.adapters.FeedsAdapter
import com.miniweam.quickread.adapters.FeedsCategoryAdapter
import com.miniweam.quickread.databinding.FragmentBookmarkBinding
import com.miniweam.quickread.databinding.FragmentHomeBinding

class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }
}