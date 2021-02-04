package com.android.ranks.ui.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.ranks.R
import com.android.ranks.databinding.NewsDetailFragmentBinding
import com.android.ranks.ui.toolbar.FragmentToolbar
import com.android.ranks.ui.toolbar.ToolbarManager
import com.android.ranks.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.news_fragment.*

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private var binding: NewsDetailFragmentBinding by autoCleared()
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = FragmentToolbar.ToolbarBuilder()
            .withId(toolbar.id)
            .withTitle(getString(R.string.news_details))
            .build()
        ToolbarManager(toolbar, view).prepareToolbar()
        setupObservers()
        arguments?.getString(getString(R.string.to_link))?.let { viewModel.start(it) }
    }

    private fun setupObservers() {
        viewModel.link.observe(viewLifecycleOwner, Observer { link ->
            binding.web.loadUrl(link)
        })
    }
}
