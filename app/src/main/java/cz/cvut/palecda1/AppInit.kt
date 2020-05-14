package cz.cvut.palecda1

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
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
        contextForInjector(this)
        setupDefaultFeeds()
        notificationChannel()
    }

    private fun notificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            //val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                //description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupDefaultFeeds(){
        if (!sharedPreferences.getBoolean(INIT_DATA_KEY, false)) {
            val feedRepo = injector.getFeedRepo()
            feedRepo.addFeed(RoomFeed(getString(R.string.default_feed_url), getString(R.string.default_feed_title), true))
            feedRepo.addFeed(RoomFeed(getString(R.string.default_feed_url2), getString(R.string.default_feed_title2), true))
            sharedPreferences.edit().putBoolean(INIT_DATA_KEY, true).apply()
        }
    }

    companion object {
        const val CHANNEL_ID = "article_diff_notification_channel"
        lateinit var injector: Injector
        fun contextForInjector(context: Context) {
            if(!this::injector.isInitialized)
                injector = Injector.generate(context)
        }
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
        /*val colorFakeLinks
            get() = ContextCompat.getColor(context, R.color.colorFakeLinks)*/
        const val TAG = "AppInit"
        fun colorToHexString(color: Int): String {
            val str = Integer.toHexString(color)
            require(str.length == 8 && str.startsWith("ff", true))
            return "#" + str.substring(2)
        }
    }
}