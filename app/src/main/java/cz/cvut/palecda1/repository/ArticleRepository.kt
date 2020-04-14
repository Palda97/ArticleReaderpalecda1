package cz.cvut.palecda1.repository

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.dao.ArticleDao
import cz.cvut.palecda1.dao.ArticleDaoFake
import cz.cvut.palecda1.dao.ArticleDaoRoom
import cz.cvut.palecda1.model.RoomArticle

class ArticleRepository(private val articleDao: ArticleDao) {

    init {
        if(articleDao is ArticleDaoRoom){
            doAsync { articleDao.deleteAll() }
        }
    }

    fun getArticleList(): LiveData<MailPackage<List<Article>>> {
        val data = MutableLiveData<MailPackage<List<Article>>>()
        val list = articleDao.articleList()
        data.value = MailPackage(list, MailPackage.OK, "")
        return data
    }

    fun getArticleById(id: String): LiveData<MailPackage<Article>> {
        val data = MutableLiveData<MailPackage<Article>>()
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
}