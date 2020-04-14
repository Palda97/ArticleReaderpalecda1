package cz.cvut.palecda1.repository

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.dao.ArticleDao
import cz.cvut.palecda1.dao.ArticleDaoRoom
import cz.cvut.palecda1.model.RoomArticle

class ArticleRepository(val articleDao: ArticleDao) {

    init {
        if(articleDao is ArticleDaoRoom){
            doAsync { articleDao.deleteAll() }
        }
    }

    fun getArticleList(): LiveData<MailPackage<List<RoomArticle>>> {
        val data = MutableLiveData<MailPackage<List<RoomArticle>>>()
        data.value = MailPackage(null, MailPackage.LOADING, "")
        //val list = articleDao.articleList()
        //data.value = MailPackage(list, MailPackage.OK, "")
        asyncGetArticles(data)
        return data
    }

    fun getArticleById(id: String): LiveData<MailPackage<RoomArticle>> {
        val data = MutableLiveData<MailPackage<RoomArticle>>()
        val article = articleDao.articleById(id)
        val status = if (article != null) MailPackage.OK else MailPackage.ERROR
        data.value = MailPackage(article, status, "")
        return data
    }

    // temporary memory leak is fine
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
    fun asyncGetArticles(data: MutableLiveData<MailPackage<List<RoomArticle>>>) {
        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                //Thread.sleep(2000)
                val list = articleDao.articleList()
                data.postValue(MailPackage(list, MailPackage.OK, ""))
                return null
            }
        }.execute()
    }
}