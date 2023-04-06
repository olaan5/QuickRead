package com.miniweam.quickread.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.miniweam.quickread.R
import com.miniweam.quickread.arch.FeedState
import com.miniweam.quickread.arch.FeedsViewModel
import com.miniweam.quickread.databinding.FragmentBookmarkBinding
import com.miniweam.quickread.databinding.FragmentNewsSummaryBinding
import com.miniweam.quickread.util.DEFAULT_DATE_FORMAT
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
@RequiresApi(Build.VERSION_CODES.O)
class NewsSummaryFragment : Fragment() {
    private var _binding: FragmentNewsSummaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<FeedsViewModel>()
    private val args by navArgs<NewsSummaryFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNewsById()
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getNewsById(){
        if (args.newsId<=0){
            Toast.makeText(requireContext(), "Invalid Index...", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.getNewsFeed(args.newsId)
        lifecycleScope.launch {
        viewModel.newsFeedFlow.collect {state->
            when(state){
                is FeedState.Loading->{
                   binding.apply {
                       titleText.text ="----"
                       authorText.text ="----"
                       timestampText.text ="----"
                       newsImg.setImageResource(R.drawable.ic_launcher_foreground)
                       emptyStateTv.isVisible = false
                       progressBar.isVisible = true
                   }
                }
                is FeedState.Successful->{
                    binding.apply {
                        titleText.text =state.newsResponseBody.data.title
                        contentText.text =state.newsResponseBody.data.content
                        authorText.text ="Unknown Author"
                        timestampText.text =getDateFormat(state.newsResponseBody.data.datePublished)
                        newsImg.load(state.newsResponseBody.data.imageUrl){
                            placeholder(R.drawable.ic_launcher_foreground)
                            error(R.drawable.error_outline_24)
                        }
                        emptyStateTv.isVisible = false
                        progressBar.isVisible = false
                    }
                }
                is FeedState.Failure->{
                    binding.apply {
                        titleText.text ="----"
                        authorText.text ="----"
                        timestampText.text ="----"
                        emptyStateTv.text = state.msg
                        newsImg.setImageResource(R.drawable.ic_launcher_foreground)
                        emptyStateTv.isVisible = true
                        progressBar.isVisible = false
                    }
                }
            }

        }


    }}


    private  fun getDateFormat(date: String):String{
        val format =
            DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)
        val localDate = LocalDateTime.parse(date, format)
        val dateFormatter = DateTimeFormatter.ofPattern("d'th' MMMM, yyyy", Locale.getDefault())
        return localDate.format(dateFormatter)
    }

}