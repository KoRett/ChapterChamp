package com.korett.network.retrofit

import com.korett.network.model.BookNetwork
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ChapterChampSource {

    @GET("popular-books")
    suspend fun getPopularBooks(): List<BookNetwork>

    @GET("popular-book")
    suspend fun getPopularBookById(@Query("id") id: Int): BookNetwork

    companion object {
        fun create(): ChapterChampSource {
            val clientBuilder = OkHttpClient.Builder()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            clientBuilder.addInterceptor(interceptor)

            val retrofit = Retrofit.Builder().baseUrl("http://10.66.66.3:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()
            return retrofit.create(ChapterChampSource::class.java)
        }
    }
}