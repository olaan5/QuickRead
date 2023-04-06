package com.miniweam.quickread.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniweam.quickread.model.Data
import com.miniweam.quickread.model.NewsData
import com.miniweam.quickread.model.QrAllResponse
import com.miniweam.quickread.model.QrNewsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FeedsViewModel : ViewModel() {
    private val repository = FeedsRepository()

    var allHomeFeedsFlow = MutableStateFlow<FeedState>(FeedState.Loading)
        private set
    var newsFeedFlow = MutableStateFlow<FeedState>(FeedState.Loading)
        private set
    var searchFeedFlow = MutableStateFlow<FeedState>(FeedState.Failure("Search Feeds..."))
//        private set

    var homeFirstRun = MutableStateFlow(true)
        private set

    fun getAllFeeds() {
        allHomeFeedsFlow.value = FeedState.Loading
        viewModelScope.launch {
            allHomeFeedsFlow.value = repository.getAllFeeds()
        }
    }

    fun getNewsFeed(id: Int) {
        newsFeedFlow.value = FeedState.Loading
        viewModelScope.launch {
            newsFeedFlow.value = repository.getNewsFeed(id)
        }
    }

    fun toggleHomeFirstRun(state: Boolean) {
        homeFirstRun.value = state
    }

    fun toggleSearchFeedState(state: FeedState){
        searchFeedFlow.value = state
    }

    fun searchFeed(query: String) {
        searchFeedFlow.value = FeedState.Loading
        when (val homeFeedsState = allHomeFeedsFlow.value) {
            is FeedState.Successful -> {
                val result = homeFeedsState.allResponseBody.data.filter {
                    it.title.contains(query,true)
                }
                if (result.isEmpty()){
                   searchFeedFlow.value = FeedState.Failure("No results found...")
                    return
                }
                searchFeedFlow.value = FeedState.Successful(QrAllResponse(result,"Success"))
            }
            is FeedState.Failure -> {
                searchFeedFlow.value = FeedState.Failure("Search Feeds...")
            }
            else -> Unit

        }
    }
}

sealed class FeedState {
    object Loading : FeedState()
    data class Failure(val msg: String) : FeedState()
    class Successful : FeedState {
        lateinit var allResponseBody: QrAllResponse
        lateinit var newsResponseBody: QrNewsResponse

        constructor(data: QrAllResponse) {
            this.allResponseBody = data
        }

        constructor(data: QrNewsResponse) {
            this.newsResponseBody = data
        }
    }
}
