package com.android.ranks.ui.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.ranks.data.entities.News
import com.android.ranks.data.remote.NewsDataSource
import com.android.ranks.data.repository.NewsRepository
import com.android.ranks.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class NewsViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository)
    : ViewModel(), CoroutineScope {
    val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val progress = MutableLiveData<Resource.Status>()
    val progressObserve: LiveData<Resource.Status> = progress
    val searchPrefix: MutableLiveData<String> = MutableLiveData()
    var newsList: LiveData<PagedList<News>> = Transformations.switchMap(searchPrefix) { paramm->
        loadNews(paramm)
    }

    fun search(param: String) {
        newsList = Transformations.switchMap(searchPrefix) { paramm->
            loadNews(paramm)
        }
        searchPrefix.value = param
    }

    fun loadNews(param: String):LiveData<PagedList<News>> {
        val config = PagedList.Config.Builder()
            .setPageSize(NewsDataSource.PER_PAGE)
            .setEnablePlaceholders(false)
            .build()
        val data = NewsDataSource(viewModelScope, newsRepository, param)
        val dataSourceFactory = object : DataSource.Factory<Int, News>() {
            override fun create(): DataSource<Int, News> {
                return data
            }
        }
        data.progressLiveStatus?.observeForever {
            progress?.postValue(it)
        }
        return LivePagedListBuilder<Int, News>(dataSourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}