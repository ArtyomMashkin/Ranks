package com.android.ranks.ui.newsdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsDetailViewModel : ViewModel() {
    val link = MutableLiveData<String>()

    fun start(linkTo: String) {
        link.value = linkTo
    }
}
