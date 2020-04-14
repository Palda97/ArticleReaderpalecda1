package cz.cvut.palecda1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.dao.ArticleDao
import cz.cvut.palecda1.dao.ArticleDaoFake

class ArticleRepository(private val articleDao: ArticleDao) {

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

    //

    /*companion object {
        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(articleDao: ArticleDao): ArticleRepository {
            return instance ?: synchronized(this){
                instance ?: ArticleRepository(articleDao).also { instance = it }
            }
        }
    }*/
}