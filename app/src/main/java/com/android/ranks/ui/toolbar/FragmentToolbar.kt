package com.android.ranks.ui.toolbar

import androidx.annotation.IdRes
import com.android.ranks.utils.Constants.Companion.EMPTY_STRING
import com.android.ranks.utils.SearchListener

class FragmentToolbar(
    @IdRes val resId: Int,
    val title: String,
    val withSearch: Boolean,
    val onSearchListener: SearchListener?
) {

    companion object {
        @JvmField
        val NO_TOOLBAR = -1
    }

    class ToolbarBuilder {
        private var resId: Int = -1
        private var title: String = EMPTY_STRING
        private var withSearch: Boolean = false
        private var onSearchListener: SearchListener? = null

        fun withId(@IdRes resId: Int) = apply { this.resId = resId }

        fun withTitle(title: String) = apply { this.title = title }

        fun withSearch(withSearch: Boolean) = apply {this.withSearch = withSearch}

        fun withOnSearchListener(onSearchListener: SearchListener?) =
            apply {
                this.onSearchListener = onSearchListener
            }

        fun build() = FragmentToolbar(
            resId = resId,
            title = title,
            withSearch = withSearch,
            onSearchListener = onSearchListener
        )
    }
}