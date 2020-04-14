package cz.cvut.palecda1

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.AsyncTask
import cz.cvut.palecda1.dao.FeedDao
import cz.cvut.palecda1.repository.AppDatabase
import cz.cvut.palecda1.repository.FeedRepository
import cz.cvut.palecda1.model.RoomFeed

/**
 * App's application class
 */
class AppInit : Application() {

    override fun onCreate() {
        super.onCreate()
        /*val sharedPreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean(INIT_DATA_KEY, false)) {
            val dao = AppDatabase.getInstance(this)
                .notesDao()
            val notesRepository = NotesRepository(dao)

            notesRepository.insertNote("First note", "First description")
            notesRepository.insertNote("Second note", "Second description")
            notesRepository.insertNote("Third note", "Third description")
            sharedPreferences.edit().putBoolean(INIT_DATA_KEY, true).apply()
        }*/
        db = AppDatabase.getInstance(this)
        asyncFeeds(db.feedDao())
        Thread.sleep(1000)
    }

    @SuppressLint("StaticFieldLeak")
    fun asyncFeeds(feedDao: FeedDao) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                feedDao.deleteAll()
                feedDao.insertFeed(RoomFeed("https://www.viralpatel.net/feed", "android devs"))
                return null
            }
        }.execute()
    }

    companion object {
        lateinit var db: AppDatabase
        /*private const val INIT_DATA_KEY = "init_data"
        private const val SP_NAME = "preferences.xml"*/
    }
}