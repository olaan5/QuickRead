package com.miniweam.quickread.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miniweam.quickread.db.QrDatabase
import com.miniweam.quickread.model.Data
import com.miniweam.quickread.model.NewsData
import com.miniweam.quickread.model.QrAllResponse
import com.miniweam.quickread.model.QrNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class FeedsViewModel(private val database: QrDatabase) : ViewModel() {
    private val repository : FeedsRepository = FeedsRepository(database)

    var allHomeFeedsFlow = MutableStateFlow<FeedState>(FeedState.Loading)
        private set
    var newsFeedFlow = MutableStateFlow<FeedState>(FeedState.Loading)
        private set
    var searchFeedFlow = MutableStateFlow<FeedState>(FeedState.Failure("Search Feeds..."))
//        private set

    var homeFirstRun = MutableStateFlow(true)
        private set

   /* fun init(appDataBase: QrDatabase) {
        repository = FeedsRepository(appDataBase)
    }*/

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

    var allBookMarkedNews = MutableStateFlow<FeedState>(FeedState.Loading)
        private set
    var bookMarkedState = MutableStateFlow(-1)
        private set

    //region DB operations

    fun getAllBookMarkedNews(){
        viewModelScope.launch {
            repository.getAllNewsFromDb().collect{
                if (it.isEmpty()){
                    allBookMarkedNews.value = FeedState.Failure("No Bookmarks Available")
                    return@collect
                }
                allBookMarkedNews.value = FeedState.Successful(it)
            }
        }
    }

    fun addToBookMarks(newsData: NewsData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNews(newsData)
            checkIfBookMarked(newsData.id)
        }
    }

    fun removeFromBookMarks(newsData: NewsData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNews(newsData)
            checkIfBookMarked(newsData.id)
        }
    }

     fun checkIfBookMarked(id:Int) {
         viewModelScope.launch(Dispatchers.IO) {
             bookMarkedState.value = repository.checkIfNewsExists(id)
         }
    }

    fun resetBookmarkedState(){
        bookMarkedState.value = -1
    }


    //endregion

}

sealed class FeedState {
    object Loading : FeedState()
    data class Failure(val msg: String) : FeedState()
    class Successful : FeedState {
        lateinit var allResponseBody: QrAllResponse
        lateinit var newsResponseBody: QrNewsResponse
        lateinit var allBookMarks: List<NewsData>

        constructor(data: QrAllResponse) {
            this.allResponseBody = data
        }

        constructor(data: QrNewsResponse) {
            this.newsResponseBody = data
        }
        constructor(data: List<NewsData>) {
            this.allBookMarks = data
        }
    }
}


class FeedsViewModelFactory(private val database: QrDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedsViewModel(database) as T
    }

}
