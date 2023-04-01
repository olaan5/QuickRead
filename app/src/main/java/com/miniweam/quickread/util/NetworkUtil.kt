package com.miniweam.quickread.util

import com.miniweam.quickread.model.QrAllResponse
import com.miniweam.quickread.model.QrNewsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private const val BASE_URL = "https://qrapi.bxpats.com/"

private val qrRetrofit = Retrofit.Builder()
    .client(getOkHttp())
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface QuickRead{

    @GET("news")
    suspend fun getAllNews():Response<QrAllResponse>
    @GET("news/{id}")
    suspend fun getNews(@Path("id") id:Int):Response<QrNewsResponse>

}
private fun getOkHttp(): OkHttpClient {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder().addInterceptor(logger).build()
}

object ApiService{
    val qrApiService: QuickRead by lazy {
        qrRetrofit.create(QuickRead::class.java)
    }
}