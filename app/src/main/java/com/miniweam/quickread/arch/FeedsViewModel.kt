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
}

sealed class FeedState {
    object Loading : FeedState()
    data class Failure(val msg: String) : FeedState()
    class Successful : FeedState {
//        lateinit var allHomeFeeds: List<Data>
//        lateinit var newsFeed: NewsData
        lateinit var allResponseBody: QrAllResponse
        lateinit var newsResponseBody: QrNewsResponse

      /*  constructor(data: List<Data>) {
            this.allHomeFeeds = data
        }

        constructor(data: NewsData) {
            this.newsFeed = data
        }*/

        constructor(data: QrAllResponse) {
            this.allResponseBody = data
        }

        constructor(data: QrNewsResponse) {
            this.newsResponseBody = data
        }
    }
}
