package cz.cvut.palecda1.services

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.util.Log
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.repository.ArticleRepository

class ArticleDownloader : JobService() {

    private lateinit var repository: ArticleRepository
    private var canceled = false
    private var params: JobParameters? = null

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "checking injector")
        AppInit.contextForInjector(this)
        repository = AppInit.injector.getArticleRepo(this)
        Log.d(TAG, "onStartJob")
        this.params = params
        workInBackground()
        return true
    }

    private fun workInBackground() {
        Thread(Runnable { run() }).start()
    }

    private fun run() {
        repository.downloadArticles()
        repository.observableLoading.postValue(false)
        jobFinished(params, false)
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        canceled = true
        return true
    }

    companion object {
        private const val JOB_ID = 420
        private const val INTERVAL: Long = 60 * 60 * 1000
        private const val TAG = "ArticleDownloader"

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
                .setPeriodic(INTERVAL)
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