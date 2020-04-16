package cz.cvut.palecda1.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.cvut.palecda1.dao.ArticleDaoRoom
import cz.cvut.palecda1.dao.FeedDaoRoom
import cz.cvut.palecda1.model.RoomArticle
import cz.cvut.palecda1.model.RoomFeed

/**
 * Application database implemented with Room
 */
@Database(entities = [RoomFeed::class, RoomArticle::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDaoRoom
    abstract fun feedDao(): FeedDaoRoom

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}