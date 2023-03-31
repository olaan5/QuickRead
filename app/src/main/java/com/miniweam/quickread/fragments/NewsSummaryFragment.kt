package com.miniweam.quickread.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miniweam.quickread.R
import com.miniweam.quickread.databinding.FragmentBookmarkBinding
import com.miniweam.quickread.databinding.FragmentNewsSummaryBinding

class NewsSummaryFragment : Fragment() {
    private var _binding: FragmentNewsSummaryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

}