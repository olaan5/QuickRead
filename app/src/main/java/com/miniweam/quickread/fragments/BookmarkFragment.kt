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
import com.miniweam.quickread.DummyItem
import com.miniweam.quickread.adapters.BookmarksAdapter
import com.miniweam.quickread.adapters.FeedsAdapter
import com.miniweam.quickread.arch.FeedState
import com.miniweam.quickread.arch.FeedsViewModel
import com.miniweam.quickread.databinding.FragmentBookmarkBinding
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class
BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val bookmarksAdapter by lazy { BookmarksAdapter() }
    private val viewModel by activityViewModels<FeedsViewModel>()
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
        binding.newsRecyclerView.adapter = bookmarksAdapter
        viewModel.getAllBookMarkedNews()
        getBookmarks()
        bookmarksAdapter.adapterClick {
            val action =
                BookmarkFragmentDirections.actionBookmarkFragmentToNewsSummaryFragment(it.id)
            findNavController().navigate(action)
        }
    }


    private fun getBookmarks() {
        lifecycleScope.launch {
            viewModel.allBookMarkedNews.collect { state ->
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
                        bookmarksAdapter.submitList(state.allBookMarks)
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
}
