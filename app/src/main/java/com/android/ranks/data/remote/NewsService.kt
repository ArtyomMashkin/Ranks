package com.android.ranks.data.remote

import com.android.ranks.data.entities.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsService {
    @POST("news/posts")
    fun getAllNews(@Query("page") page: Int, @Query("per_page") per_page: Int,
                   @Query("search") search: String) : Deferred<NewsResponse>
}