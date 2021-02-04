package com.android.ranks.ui.adapter

import android.view.View
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ItemType>(diffCallback: DiffUtil.ItemCallback<ItemType>) :
    PagedListAdapter<ItemType, BaseViewHolder<ItemType>>(diffCallback) {

    override fun onBindViewHolder(holder: BaseViewHolder<ItemType>, position: Int) {
        getItem(position)?.let { holder.bindView(it) }
    }
}

abstract class BaseViewHolder<ItemType>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindView(item: ItemType)
}
