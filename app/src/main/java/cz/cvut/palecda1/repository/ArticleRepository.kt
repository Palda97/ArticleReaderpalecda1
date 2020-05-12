package cz.cvut.palecda1.repository

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException
import cz.cvut.palecda1.R
import cz.cvut.palecda1.dao.ArticleDao
import cz.cvut.palecda1.model.RoomArticle
import java.io.IOException
import java.net.MalformedURLException

class ArticleRepository(
    val roomDao: ArticleDao,
    val networkDao: ArticleDao,
    val application: Application
) {

    val observableArticles: MediatorLiveData<MailPackage<List<RoomArticle>>> = MediatorLiveData()
    val observableArticle: MediatorLiveData<MailPackage<RoomArticle>> = MediatorLiveData()

    fun addSourceList(data: MutableLiveData<MailPackage<List<RoomArticle>>>){
        observableArticles.addSource(data) { observableArticles.value = it }
    }
    fun addSourceSingle(data: MutableLiveData<MailPackage<RoomArticle>>){
        observableArticle.addSource(data) { observableArticle.value = it }
    }

    fun downloadArticles(): LiveData<MailPackage<List<RoomArticle>>> {
        Log.d(TAG, "downloadArticles")
        val data = MutableLiveData<MailPackage<List<RoomArticle>>>()
        data.value = MailPackage.loadingPackage()
        asyncDownloadArticles(data)
        addSourceList(data)
        return data
    }

    fun getArticleList(): LiveData<MailPackage<List<RoomArticle>>> {
        Log.d(TAG, "getArticleList")
        //return downloadArticles()
        val data = MutableLiveData<MailPackage<List<RoomArticle>>>()
        data.value = MailPackage.loadingPackage()
        asyncLoadArticles(data)
        addSourceList(data)
        return data
    }

    fun saveToDb(list: List<RoomArticle>){
        Log.d(TAG, "saveToDb")
        doAsync { roomDao.clearAndInsertList(list) }
    }

    fun getArticleById(url: String): LiveData<MailPackage<RoomArticle>> {
        Log.d(TAG, "getArticleById")
        val data = MutableLiveData<MailPackage<RoomArticle>>()
        data.value = MailPackage.loadingPackage()
        asyncGetById(data, url)
        addSourceSingle(data)
        return data
    }

    @SuppressLint("StaticFieldLeak")
    fun asyncLoadArticles(data: MutableLiveData<MailPackage<List<RoomArticle>>>) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val list = roomDao.articleList()
                val mail = MailPackage(
                    list,
                    MailPackage.OK,
                    ""
                )
                data.postValue(mail)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun asyncGetById(data: MutableLiveData<MailPackage<RoomArticle>>, url: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val article = roomDao.articleById(url)
                val mail: MailPackage<RoomArticle> =
                    MailPackage(
                        article,
                        if (article == null) MailPackage.ERROR else MailPackage.OK,
                        ""
                    )
                data.postValue(mail)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun doAsync(run: () -> Unit) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                run()
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun asyncDownloadArticles(data: MutableLiveData<MailPackage<List<RoomArticle>>>) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                //Thread.sleep(5000)
                var mail: MailPackage<List<RoomArticle>>
                try {
                    val list = networkDao.articleList()
                    mail = MailPackage(
                        list,
                        MailPackage.OK,
                        ""
                    )
                    saveToDb(list)
                } catch (e: MalformedURLException) {
                    mail = MailPackage(
                        null,
                        MailPackage.ERROR,
                        application.getString(R.string.MalformedURL) +
                                "\n${e.message}"
                    )
                    e.printStackTrace()
                } catch (e: IllegalArgumentException) {
                    mail = MailPackage(
                        null,
                        MailPackage.ERROR,
                        application.getString(R.string.IllegalArgument) +
                                "\n${e.message}"
                    )
                    e.printStackTrace()
                } catch (e: FeedException) {
                    mail = MailPackage(
                        null,
                        MailPackage.ERROR,
                        application.getString(R.string.FeedException) +
                                "\n${e.message}"
                    )
                    e.printStackTrace()
                } catch (e: IOException) {
                    mail = MailPackage(
                        null,
                        MailPackage.ERROR,
                        application.getString(R.string.IOException) +
                                "\n${e.message}"
                    )
                    e.printStackTrace()
                }
                data.postValue(mail)
                return null
            }
        }.execute()
    }

    companion object {
        const val TAG = "ArticleRepository"
    }
}