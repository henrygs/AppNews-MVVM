package com.henry.appnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.henry.appnews.R
import com.henry.appnews.databinding.ItemNewsBinding
import com.henry.appnews.model.Article
import kotlinx.android.synthetic.main.item_news.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
           ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        with(holder){
            with(differ.currentList[position]){
                Glide.with(holder.itemView.context).load(urlToImage).into(binding.ivArticleImage)
                binding.tvTitle.text = author ?: source?.name
                binding.tvSource.text = source?.name ?: author
                binding.tvDescription.text = description
                binding.tvPublished.text = publishedAt

                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(this)
                    }
                }
            }
        }

//        val article = differ.currentList[position]
//        holder.itemView.apply {
//            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
//            tvTitle.text = article.author ?: article.source?.name
//            tvSource.text = article.source?.name ?: article.author
//            tvDescription.text = article.description
//            tvPublished.text = article.publishedAt
//
//            setOnClickListener {
//                onItemClickListener?.let { click ->
//                    click(article)
//                }
//            }
//        }
    }

    private var onItemClickListener: ((Article) -> Unit) ? = null

    fun setOnclickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}