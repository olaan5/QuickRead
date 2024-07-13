package com.miniweam.quickread.arch

import android.util.Log
import com.miniweam.quickread.db.QrDatabase
import com.miniweam.quickread.model.Data
import com.miniweam.quickread.model.NewsData
import com.miniweam.quickread.util.ApiService
import kotlinx.coroutines.flow.Flow

class FeedsRepository(private val qrDb:QrDatabase) {
    private val qrDao = qrDb.qrDao()
    suspend fun getAllFeeds(): FeedState {
        return try {
            val body = ApiService.qrApiService.getAllNews()
            Log.d("JOE", "API RESPONSE (size: ${body.body()?.data?.size}): ${body.body()} ")
            body.body()?.let { FeedState.Successful(it) }
                ?: FeedState.Failure("Response not available at the moment....")

        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE : ERROR-> $e ")
            FeedState.Failure(e.toString())
        }
    }

    suspend fun getNewsFeed(id:Int): FeedState {
        return try {
            val body = ApiService.qrApiService.getNews(id)
            Log.d("JOE", "API RESPONSE (single): ${body.body()} ")
            body.body()?.let { FeedState.Successful(it) }
                ?: FeedState.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            FeedState.Failure(e.toString())
        }
    }

    fun getAllNewsFromDb()= qrDao.getAllNews()

    suspend fun insertNews(newsData: NewsData){
        qrDao.insertNews(newsData)
    }

    suspend fun deleteNews(newsData: NewsData){
        qrDao.deleteNews(newsData.id)
    }

    suspend fun checkIfNewsExists(id: Int) = qrDao.checkIfNewsExists(id)
}