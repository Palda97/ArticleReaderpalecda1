package cz.cvut.palecda1.services

import android.app.job.JobParameters
import android.app.job.JobService
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.repository.ArticleRepository

class ArticleDownloader : JobService() {

    private val repository: ArticleRepository = AppInit.injector.getArticleRepo()!!
    private var canceled = false

    private var params: JobParameters? = null

    override fun onStartJob(params: JobParameters?): Boolean {
        this.params = params
        workInBackground()
        return true
    }

    private fun workInBackground() {
        Thread(Runnable { run() }).start()
    }

    private fun run(){
        repository.downloadArticles()
        jobFinished(params, false)
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        canceled = true
        return true
    }
}