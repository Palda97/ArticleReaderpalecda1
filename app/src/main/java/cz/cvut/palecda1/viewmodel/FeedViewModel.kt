package cz.cvut.palecda1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.model.RoomFeed
import cz.cvut.palecda1.repository.FeedRepository

//class ArticleViewModel(application: Application) : AndroidViewModel(application) {
class FeedViewModel(private val repository: FeedRepository) : ViewModel() {
    val observableFeeds: MediatorLiveData<MailPackage<List<RoomFeed>>> = MediatorLiveData()

    val feedsLiveData: LiveData<MailPackage<List<RoomFeed>>>
        get() = observableFeeds

    init {
        observableFeeds.value = null
    }

    fun loadFeeds() {
        val feeds = repository.getFeedList()
        observableFeeds.addSource(feeds) { observableFeeds.value = it }
    }

    fun addFeed(roomFeed: RoomFeed){
        repository.addFeed(roomFeed)
    }
}