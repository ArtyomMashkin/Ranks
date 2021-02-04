package com.android.ranks.data.repository

import com.android.ranks.data.UseCaseResult
import com.android.ranks.data.entities.NewsResponse
import com.android.ranks.data.remote.NewsService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {

    suspend fun getNews(page: Int, per_page: Int, search: String): UseCaseResult<NewsResponse> {
        return try {
            val result = newsService.getAllNews(page, per_page, search).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}