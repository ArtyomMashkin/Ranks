package com.android.ranks.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.android.ranks.data.entities.News
import com.android.ranks.databinding.ItemNewsBinding
import com.android.ranks.databinding.ItemPromotionBinding
import com.android.ranks.utils.Constants.Companion.DATE_FORMAT
import com.android.ranks.utils.TextUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class NewsAdapter(private val listener: NewsItemListener): BaseAdapter<News>(CONTACTS_DIFF) {

    interface NewsItemListener {
        fun onClicked(news: News)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseContactsViewHolder {
        return if (viewType == 0) {
            val binding: ItemPromotionBinding = ItemPromotionBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
            PromotionViewHolder(binding, listener)
        } else {
            val binding: ItemNewsBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
            NewsItemViewHolder(binding, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.id == null) 0 else 1
    }

    override fun onBindViewHolder(holder: BaseViewHolder<News>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = getItem(position) as News
        if (getItemViewType(position) == 0) {
            val viewHolder = holder as PromotionViewHolder
            viewHolder.bindView(data)
        } else {
            val viewHolder = holder as NewsItemViewHolder
            viewHolder.bindView(data)
        }
    }

    companion object {
        private val CONTACTS_DIFF = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean = oldItem.equals(newItem)
        }
    }

    override fun getItemCount(): Int {
        return currentList?.size ?: 0
    }
}

abstract class BaseContactsViewHolder(itemView: View) : BaseViewHolder<News>(itemView) {

    override fun bindView(item: News) {
    }
}

class NewsItemViewHolder(private val itemBinding: ItemNewsBinding,
                             private val listener: NewsAdapter.NewsItemListener)
    : BaseContactsViewHolder(itemBinding.root), View.OnClickListener {

    private lateinit var news: News

    init {
        itemBinding.root.setOnClickListener(this)
    }

    override fun bindView(item: News) {
        this.news = item
        itemBinding.name.text = item.title
        itemBinding.nameAuth.text = item.author?.name
        itemBinding.description.text = item.description
        Glide.with(itemBinding.root)
            .load(item.author?.image?.src)
            .transform(CircleCrop())
            .into(itemBinding.image)
        Glide.with(itemBinding.root)
            .load(item.image?.src)
            .into(itemBinding.imageMain)
        if (item.date != null) {
            itemBinding.date.text = TextUtil.getFormattedDate(item.date, DATE_FORMAT)
        }
    }

    override fun onClick(p0: View?) {
        listener.onClicked(news)
    }
}

class PromotionViewHolder(private val itemBinding: ItemPromotionBinding,
                          private val listener: NewsAdapter.NewsItemListener)
    : BaseContactsViewHolder(itemBinding.root), View.OnClickListener {

    private lateinit var news: News

    init {
        itemBinding.goPromotion.setOnClickListener(this)
    }

    override fun bindView(item: News) {
        this.news = item
    }

    override fun onClick(p0: View?) {
        listener.onClicked(news)
    }
}