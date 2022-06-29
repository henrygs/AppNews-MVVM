package com.henry.appnews.ui.fragment.favorite

import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.henry.appnews.R
import com.henry.appnews.data.local.db.ArticleDataBase
import com.henry.appnews.data.remote.RetrofitInstance
import com.henry.appnews.databinding.FragmentFavoriteBinding
import com.henry.appnews.repository.NewsRepository
import com.henry.appnews.ui.adapter.MainAdapter
import com.henry.appnews.ui.fragment.base.BaseFragment
import com.henry.appnews.utils.state.ArticlListEvent
import com.henry.appnews.utils.state.ArticleListState
import com.henry.appnews.utils.state.StateResource

class FavoriteFragment : BaseFragment<FavoriteViewModel, FragmentFavoriteBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun getViewModel(): Class<FavoriteViewModel> = FavoriteViewModel::class.java

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDataBase.invoke(requireContext()))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatch(ArticlListEvent.Fetch)

        confiRecycleView()
        observerResults()
    }


    private fun observerResults() {
        viewModel.favorite.observe(viewLifecycleOwner, { response ->
            when (response) {
                is ArticleListState.Success -> {
                    binding.tvEmptyList.visibility = View.INVISIBLE

                    mainAdapter.differ.submitList(response.list)

                }
                is ArticleListState.ErrorMessage -> {
                    binding.tvEmptyList.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Ocorreu um erro: ${response.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ArticleListState.Loading -> {
                    binding.rvFavorite.visibility = View.VISIBLE
                }

                is ArticleListState.Empty -> {
                    binding.tvEmptyList.visibility = View.VISIBLE
                    mainAdapter.differ.submitList(emptyList())
                }
            }
        })
    }

    private val itemTouchPerCallbak = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val article = mainAdapter.differ.currentList[position]
            viewModel.deliteArticle(article)
            Snackbar.make(
                viewHolder.itemView, R.string.article_delete_successful,
                Snackbar.LENGTH_SHORT
            ).apply {
                setAction(getString(R.string.undo)) {
                    viewModel.saveArticle(article)
                    mainAdapter.notifyDataSetChanged()
                }
                show()
            }
            observerResults()
        }
    }


    private fun confiRecycleView() {
        with(binding.rvFavorite) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            ItemTouchHelper(itemTouchPerCallbak).attachToRecyclerView(this)
        }
        mainAdapter.setOnclickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
}