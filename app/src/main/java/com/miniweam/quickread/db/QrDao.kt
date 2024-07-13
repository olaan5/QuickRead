package com.miniweam.quickread.db

import androidx.room.*
import com.miniweam.quickread.model.NewsData
import kotlinx.coroutines.flow.Flow


@Dao
interface QrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsData: NewsData)

    @Query("SELECT * FROM saved_news_table")
    fun getAllNews(): Flow<List<NewsData>>

    @Query("DELETE FROM saved_news_table")
    suspend fun deleteAll()

    @Query("DELETE FROM saved_news_table WHERE id=:id ")
    suspend fun deleteNews(id: Int)

    /*@Delete
    // doesn't work on this current version and switching to 2.4.2,
     renders 'deleteAll()[@Query("DELETE FROM saved_news_table")]' obsolete
    suspend fun deleteNews(newsData: NewsData)
*/
    @Query("SELECT COUNT() FROM saved_news_table WHERE id=:id")
    suspend fun checkIfNewsExists(id: Int): Int


    /**
     * get all
     * insert
     * delete
     * check if exists
     * ----------------------
     * delete all
     * */
}