package cz.cvut.palecda1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.repository.ArticleRepository

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    val observableArticles: MediatorLiveData<MailPackage<List<Article>>> = MediatorLiveData()
    val observableArticle: MediatorLiveData<MailPackage<Article>> = MediatorLiveData()
    val repository = ArticleRepository()

    val articlesLiveData: LiveData<MailPackage<List<Article>>>
        get() = observableArticles
    val articleLiveData: LiveData<MailPackage<Article>>
        get() = observableArticle

    init {
        observableArticles.value = null //TODO zkusit co se stane bez tehle radky
        observableArticle.value = null
    }

    fun loadArticles() {
        val articles = repository.getArticleList()
        observableArticles.addSource(articles) { observableArticles.value = it }
    }

    fun loadArticleById(id: String) {
        val article = repository.getArticleById(id)
        observableArticle.addSource(article) { observableArticle.value = it }
    }
}