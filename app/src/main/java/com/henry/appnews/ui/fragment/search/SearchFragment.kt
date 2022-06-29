package com.henry.appnews.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.henry.appnews.data.local.db.ArticleDataBase
import com.henry.appnews.data.remote.RetrofitInstance
import com.henry.appnews.databinding.FragmentSearchBinding
import com.henry.appnews.repository.NewsRepository
import com.henry.appnews.ui.adapter.MainAdapter
import com.henry.appnews.ui.fragment.base.BaseFragment
import com.henry.appnews.ui.fragment.home.HomeFragmentDirections
import com.henry.appnews.utils.UtilQueryTextListener
import com.henry.appnews.utils.state.StateResource

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun getViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDataBase.invoke(requireContext()))

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        search()
        observerResults()
    }

    private fun search(){
        binding.searchNews.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ){ newText ->
                newText?.let { query ->
                    if(query.isNotEmpty()){
                        viewModel.fetchSearch(query)
                        binding.rvProgressBarSearch.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun setupRecycleView() = with(binding){
        rvSearch.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

        mainAdapter.setOnclickListener { action ->
            val action =
                SearchFragmentDirections.actionSearchFragmentToWebViewFragment(action)
            findNavController().navigate(action)
        }
    }

    private fun observerResults(){
        viewModel.search.observe(viewLifecycleOwner, { response ->
            when(response){
                is StateResource.Success -> {
                    binding.rvProgressBarSearch.visibility = View.INVISIBLE
                    response.data?.let { data ->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }
                }
                is StateResource.Error -> {
                    binding.rvProgressBarSearch.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Ocorreu um erro: ${response.message.toString()}", Toast.LENGTH_SHORT).show()
                }
                is StateResource.Loading -> {
                    binding.rvProgressBarSearch.visibility = View.VISIBLE
                }
            }
        })
    }
}