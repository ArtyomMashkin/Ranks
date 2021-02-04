package com.android.ranks.ui.toolbar

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import com.android.ranks.R
import com.android.ranks.utils.Constants.Companion.EMPTY_STRING

class ToolbarManager constructor(
    private var builder: FragmentToolbar,
    private var container: View
) {

    fun prepareToolbar() {
        if (builder.resId != FragmentToolbar.NO_TOOLBAR) {
            val fragmentToolbar = container.findViewById<ViewGroup>(builder.resId)

            if (builder.title != EMPTY_STRING) {
                val textView = fragmentToolbar.findViewById<TextView>(R.id.title_text)
                textView?.text = builder.title
            }
            if (builder.withSearch) {
                val search = fragmentToolbar.findViewById<RelativeLayout>(R.id.search)
                val searchImage = fragmentToolbar.findViewById<ImageView>(R.id.search_image)
                val field = fragmentToolbar.findViewById<EditText>(R.id.search_field)
                field.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        builder.onSearchListener!!.onSearch(field.text.toString())
                        false
                    } else false
                })
                search.visibility = View.VISIBLE
                if (builder.onSearchListener != null){
                    searchImage.setOnClickListener {
                        builder.onSearchListener!!.onSearch(field.text.toString())
                    }
                }
                field.clearFocus()
            }
        }
    }
}