package com.henry.appnews.ui.fragment.home

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
import com.henry.appnews.databinding.FragmentHomeBinding
import com.henry.appnews.repository.NewsRepository
import com.henry.appnews.ui.adapter.MainAdapter
import com.henry.appnews.ui.fragment.base.BaseFragment
import com.henry.appnews.utils.state.StateResource

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mainAdapater by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        observerResults()
    }

    private fun observerResults(){
        viewModel.getAll.observe(viewLifecycleOwner, { response ->
            when(response){
                is StateResource.Success -> {
                    binding.rvProgressBar.visibility = View.INVISIBLE
                    response.data?.let { data ->
                        mainAdapater.differ.submitList(data.articles.toList())
                    }
                }
                is StateResource.Error -> {
                    binding.rvProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Ocorreu um erro: ${response.message.toString()}", Toast.LENGTH_SHORT).show()
                }
                is StateResource.Loading -> {
                    binding.rvProgressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupRecycleView() = with(binding){
        rvNews.apply {
            adapter = mainAdapater
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

        mainAdapater.setOnclickListener { action ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToWebViewFragment(action)
            findNavController().navigate(action)
        }
    }

    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDataBase.invoke(requireContext()))

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}