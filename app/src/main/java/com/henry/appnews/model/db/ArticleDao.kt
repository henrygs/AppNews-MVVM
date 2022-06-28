package com.henry.appnews.model.db

import androidx.room.*
import com.henry.appnews.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article) : Long

    @Query("SELECT * FROM articles ORDER BY id") //aponta para Article com a notação @Entity(tableName = "articles")
    fun getAll(): List<Article>

    @Delete
    suspend fun delete(article: Article)
}