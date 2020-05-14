package cz.cvut.palecda1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.repository.MailPackage
import cz.cvut.palecda1.model.RoomArticle

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
//class ArticleViewModel(private val repository: ArticleRepository) : ViewModel() {

    val repository = AppInit.injector.getArticleRepo(application)

    val articlesLiveData: LiveData<MailPackage<List<RoomArticle>>>
        get() = repository.observableArticles
    val roomArticleLiveData: LiveData<MailPackage<RoomArticle>>
        get() = repository.observableArticle

    val observableLoading: LiveData<Boolean>
        get() = repository.observableLoading

    /*fun loadArticles() {
        repository.getArticleList()
    }*/

    fun refreshArticles(){
        //repository.downloadArticles()
        repository.observableLoading.value = true
        repository.useArticleDownloader()
    }

    fun loadArticleById(id: String) {
        repository.getArticleById(id)
    }
}

/*class ArticleViewModel(application: Application) : AndroidViewModel(application) {
//class ArticleViewModel(private val repository: ArticleRepository) : ViewModel() {

    val repository = AppInit.injector.getArticleRepo(application)

    private val observableArticles: MediatorLiveData<MailPackage<List<RoomArticle>>> = MediatorLiveData()
    private val observableRoomArticle: MediatorLiveData<MailPackage<RoomArticle>> = MediatorLiveData()

    val articlesLiveData: LiveData<MailPackage<List<RoomArticle>>>
        get() = observableArticles
    val roomArticleLiveData: LiveData<MailPackage<RoomArticle>>
        get() = observableRoomArticle

    init {
        observableArticles.value = null
        observableRoomArticle.value = null
    }

    fun loadArticles() {
        val articles = repository.getArticleList()
        observableArticles.addSource(articles) { observableArticles.value = it }
    }

    fun refreshArticles(){
        val articles = repository.downloadArticles()
        observableArticles.addSource(articles) { observableArticles.value = it }
    }

    fun loadArticleById(id: String) {
        val article = repository.getArticleById(id)
        observableRoomArticle.addSource(article) { observableRoomArticle.value = it }
    }
}*/