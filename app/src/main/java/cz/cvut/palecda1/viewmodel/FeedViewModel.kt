package cz.cvut.palecda1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import cz.cvut.palecda1.MyInjector
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.model.RoomFeed

class FeedViewModel(application: Application) : AndroidViewModel(application) {
//class FeedViewModel(private val repository: FeedRepository) : ViewModel() {
    val repository = MyInjector.getFeedRepo()

    private val observableFeeds: MediatorLiveData<MailPackage<List<RoomFeed>>> = MediatorLiveData()

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

    fun deleteFeed(roomFeed: RoomFeed){
        repository.deleteFeed(roomFeed)
    }

    fun deleteAll(){
        repository.deleteAll()
    }
}