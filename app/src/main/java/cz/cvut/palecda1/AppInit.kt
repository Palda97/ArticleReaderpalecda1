package cz.cvut.palecda1

import android.app.Application
import android.content.Context
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
        clearFeeds()
        fillFeeds()
    }

    fun clearFeeds(){
        val repo = FeedRepository(db.feedDao())
        repo.deleteAll()
        Thread.sleep(1000)
    }
    fun fillFeeds(){
        val repo = FeedRepository(db.feedDao())
        repo.addFeed(RoomFeed("https://www.viralpatel.net/feed", "android devs"))
        Thread.sleep(1000)
    }

    companion object {
        lateinit var db: AppDatabase
        /*private const val INIT_DATA_KEY = "init_data"
        private const val SP_NAME = "preferences.xml"*/
    }
}