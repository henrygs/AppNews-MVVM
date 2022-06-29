package com.henry.appnews.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.henry.appnews.data.local.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDataBase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao


    companion object {

        @Volatile
        private var instance: ArticleDataBase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createdDatabase(context).also {
                instance = it
            }
        }

        private fun createdDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDataBase::class.java,
                "article_db.db"
            ).build()


    }

}