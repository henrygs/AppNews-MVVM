package com.henry.appnews.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.henry.appnews.data.local.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article) : Long

    @Query("SELECT * FROM articles ORDER BY id") //aponta para Article com a notação @Entity(tableName = "articles")
    fun getAll(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}