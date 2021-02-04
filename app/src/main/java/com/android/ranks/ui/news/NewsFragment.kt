package com.android.ranks.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.ranks.R
import com.android.ranks.data.entities.News
import com.android.ranks.databinding.NewsFragmentBinding
import com.android.ranks.ui.adapter.NewsAdapter
import com.android.ranks.ui.toolbar.FragmentToolbar
import com.android.ranks.ui.toolbar.ToolbarManager
import com.android.ranks.utils.Constants.Companion.EMPTY_STRING
import com.android.ranks.utils.Constants.Companion.LINK_PROMOTION
import com.android.ranks.utils.Resource
import com.android.ranks.utils.SearchListener
import com.android.ranks.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.news_fragment.*

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.NewsItemListener, SearchListener {

    private var binding: NewsFragmentBinding by autoCleared()
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = FragmentToolbar.ToolbarBuilder()
            .withId(toolbar.id)
            .withTitle(getString(R.string.news))
            .withSearch(true)
            .withOnSearchListener(this)
            .build()
        ToolbarManager(toolbar, view).prepareToolbar()
        setupRecyclerView()
        setupObservers()
    }

    override fun onSearch(text: String) {
        viewModel.search(text)
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter(this)
        val manager = LinearLayoutManager(requireContext())
        binding.newsList.layoutManager = manager
        binding.newsList.adapter = adapter
        binding.newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.newsList.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList != null) {
                adapter.submitList(newsList)
            }
        })
        viewModel.progressObserve.observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {
                when (list) {
                    Resource.Status.LOADING -> {
                        if (!binding.newsList.canScrollVertically(1)) {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    Resource.Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        })
        viewModel.search(EMPTY_STRING)
    }

    override fun onClicked(news: News) {
        if (news.id != null) {
            findNavController().navigate(
                R.id.action_newsFragment_to_newsDetailFragment,
                bundleOf(getString(R.string.to_link ) to news.link)
            )
        } else {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PROMOTION))
            startActivity(browserIntent)
        }
    }
}
