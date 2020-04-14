package cz.cvut.palecda1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import cz.cvut.palecda1.MyInjector
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.repository.ArticleRepository

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
//class ArticleViewModel(private val repository: ArticleRepository) : ViewModel() {

    val repository = MyInjector.getArticleRepo(application)

    val observableArticles: MediatorLiveData<MailPackage<List<Article>>> = MediatorLiveData()
    val observableArticle: MediatorLiveData<MailPackage<Article>> = MediatorLiveData()

    val articlesLiveData: LiveData<MailPackage<List<Article>>>
        get() = observableArticles
    val articleLiveData: LiveData<MailPackage<Article>>
        get() = observableArticle

    init {
        observableArticles.value = null
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