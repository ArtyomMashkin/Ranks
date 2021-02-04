package com.android.ranks.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.ranks.data.UseCaseResult
import com.android.ranks.data.entities.News
import com.android.ranks.data.repository.NewsRepository
import com.android.ranks.utils.Resource
import kotlinx.coroutines.*
import java.util.ArrayList

class NewsDataSource constructor(private val scope: CoroutineScope, private val newsRepository: NewsRepository,
private val searchPref: String) :
    PageKeyedDataSource<Int, News>() {

    val progressLiveStatus: MutableLiveData<Resource.Status>? = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        scope.launch {
                try {
                    progressLiveStatus?.postValue(Resource.Status.LOADING)
                    val response = withContext(Dispatchers.IO) {
                        newsRepository.getNews(START_PAGE, PER_PAGE, searchPref)
                    }
                    when (response) {
                        is UseCaseResult.Success -> {
                            val list = ArrayList<News>()
                            var i = 0
                            for (item in response.data.data) {
                                if ((i - 3) % 10 == 0) {
                                    list.add(News(null, null, null, null,null,
                                        null ,null, null, null))
                                }
                                list.add(item)
                                i++
                            }
                            callback.onResult(list, START_PAGE, START_PAGE + 1)
                            progressLiveStatus?.postValue(Resource.Status.SUCCESS)
                        }
                        is UseCaseResult.Error -> {
                            callback.onResult(List(0) { null }, null, null)
                            progressLiveStatus?.postValue(Resource.Status.ERROR)
                        }
                    }
                } catch (ex: Exception) {
                    callback.onResult(List(0) { null }, null, null)
                    progressLiveStatus?.postValue(Resource.Status.ERROR)
                }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        scope.launch {
            try {
                progressLiveStatus?.postValue(Resource.Status.LOADING)
                val response = withContext(Dispatchers.IO) {
                    newsRepository.getNews(params.key, PER_PAGE, searchPref)
                }
                when (response) {
                    is UseCaseResult.Success -> {
                            val list = ArrayList<News>()
                            var i = 0
                            for (item in response.data.data) {
                                if ((i - 3) % 10 == 0) {
                                    list.add(News(null, null, null, null,null,
                                        null ,null, null, null))
                                }
                                list.add(item)
                                i++
                            }
                        callback.onResult(list, params.key + 1)
                        progressLiveStatus?.postValue(Resource.Status.SUCCESS)
                    }
                    is UseCaseResult.Error ->  {
                        callback.onResult(List(0) { null }, null)
                        progressLiveStatus?.postValue(Resource.Status.ERROR)
                    }
                }
            } catch (ex: Exception) {
                callback.onResult(List(0) { null }, null)
                progressLiveStatus?.postValue(Resource.Status.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {

    }

    companion object {
        const val START_PAGE = 1
        const val PER_PAGE = 20
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}