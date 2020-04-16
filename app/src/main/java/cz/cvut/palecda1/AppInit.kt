package cz.cvut.palecda1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import cz.cvut.palecda1.repository.AppDatabase
import cz.cvut.palecda1.model.RoomFeed

/**
 * App's application class
 */
class AppInit : Application() {

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        toggleNightMode(true)
        db = AppDatabase.getInstance(this)
        if (!sharedPreferences.getBoolean(INIT_DATA_KEY, false)) {
            val feedRepo = MyInjector.getFeedRepo()
            feedRepo.addFeed(RoomFeed(getString(R.string.default_feed_url), getString(R.string.default_feed_title)))
            /*val articleRepo = MyInjector.getArticleRepo(this)
            articleRepo.downloadArticles()
            Thread.sleep(2000)*/
            sharedPreferences.edit().putBoolean(INIT_DATA_KEY, true).apply()
        }
    }

    companion object {
        lateinit var db: AppDatabase
        private const val INIT_DATA_KEY = "INIT_DATA_KEY"
        private const val SP_NAME = "preferences.xml"
        lateinit var sharedPreferences: SharedPreferences
        private const val DAYNIGHT = "DAYNIGHT"

        fun toggleNightMode(dont: Boolean = false){
            val night = sharedPreferences.getBoolean(DAYNIGHT, false)
            if(night == dont){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            if(!dont)
                sharedPreferences.edit().putBoolean(DAYNIGHT, !night).apply()
        }
    }
}