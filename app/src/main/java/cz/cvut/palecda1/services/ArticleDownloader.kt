package cz.cvut.palecda1.services

import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.NotificationTextFactory
import cz.cvut.palecda1.R
import cz.cvut.palecda1.model.RoomArticle
import cz.cvut.palecda1.repository.ArticleRepository
import cz.cvut.palecda1.view.activity.MainActivity

class ArticleDownloader : JobService() {

    private lateinit var repository: ArticleRepository
    private var canceled = false
    private var params: JobParameters? = null

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "checking injector")
        AppInit.importantInits(this)
        repository = AppInit.injector.getArticleRepo(this)
        Log.d(TAG, "onStartJob")
        this.params = params
        workInBackground()
        return true
    }

    private fun workInBackground() {
        Thread(Runnable { run() }).start()
    }

    private fun makeNotification(list: List<RoomArticle>) {
        if(list.isEmpty() || repository.observableLoading.value == true)
            return
        val ntf = NotificationTextFactory()
        val currentlyDownloaded = ntf.fromArticleListToMap(list)
        val alreadyNew = ntf.fromJsonToMap(AppInit.getAlreadyNew())
        val mix = ntf.mergeMaps(currentlyDownloaded, alreadyNew)
        val smallText = ntf.fromMapToString(mix)
        val json = ntf.fromMapToJson(mix)
        AppInit.setAlreadyNew(json)
        Log.d(TAG, "notification small text: $smallText")
        Log.d(TAG, "json for already new:\n$json")
        buildNotification(smallText, smallText)
    }

    private fun buildNotification(smallText: String, bigText: String) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, AppInit.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_rss_feed_black_24dp)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(smallText)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(bigText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        //startForeground(NOTIFICATION_ID, builder.build())
        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun run() {
        val diff = repository.downloadArticles()
        diff?.let { makeNotification(it) }
        repository.observableLoading.postValue(false)
        jobFinished(params, false)
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        canceled = true
        return true
    }

    companion object {
        private const val JOB_ID = 420
        //-------------------------------- h -> m -> s ->  ms
        //private const val INTERVAL: Long = 1 * 15 * 60 * 1000
        //private const val INTERVAL: Long = 6 * 60 * 60 * 1000
        private const val DEFAULT_INTERVAL: Long = 6 * 60 * 60 * 1000
        const val SERVICE_INTERVAL_KEY = "SERVICE_INTERVAL_KEY"
        private fun getInterval(): Long = AppInit.sharedPreferences.getLong(SERVICE_INTERVAL_KEY, DEFAULT_INTERVAL)
        private const val TAG = "ArticleDownloader"
        private const val NOTIFICATION_ID = 420

        private data class SchedPrep(val jobScheduler: JobScheduler, val builder: JobInfo.Builder)

        private fun schedulePrep(context: Context): SchedPrep {
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName = ComponentName(context, this::class.java.declaringClass!!)
            val builder = JobInfo.Builder(JOB_ID, componentName)
            return SchedPrep(jobScheduler, builder)
        }

        fun wifiReqStart(application: Context) {
            val (jobScheduler, builder) = schedulePrep(application)
            val info = builder
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build()
            jobScheduler.schedule(info)
        }

        fun makeMePeriodic(context: Context) {
            val (jobScheduler, builder) = schedulePrep(context)
            val info = builder
                .setPersisted(true)
                .setPeriodic(getInterval())
                .build()
            jobScheduler.schedule(info)
        }

        fun stop(context: Context): Boolean {
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            var ret = false
            jobScheduler.allPendingJobs.forEach {
                if (it.id == JOB_ID) {
                    Log.d(TAG, "job stopped")
                    ret = true
                }
            }
            jobScheduler.cancel(JOB_ID)
            return ret
        }
    }
}