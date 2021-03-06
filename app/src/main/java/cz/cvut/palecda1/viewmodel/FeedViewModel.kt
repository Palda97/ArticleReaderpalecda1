package cz.cvut.palecda1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.repository.MailPackage
import cz.cvut.palecda1.model.RoomFeed

class FeedViewModel(application: Application) : AndroidViewModel(application) {
//class FeedViewModel(private val repository: FeedRepository) : ViewModel() {
    val repository = AppInit.injector.getFeedRepo()
    //val articleRepo = AppInit.injector.getArticleRepo(application)

    //private val observableFeeds: MediatorLiveData<MailPackage<List<RoomFeed>>> = MediatorLiveData()

    val feedsLiveData: LiveData<MailPackage<List<RoomFeed>>>
        //get() = observableFeeds
        get() = repository.observableFeeds

    init {
        //observableFeeds.value = null
    }

    fun loadFeeds() {
        //val feeds = repository.getFeedList()
        repository.getFeedList()
        //observableFeeds.addSource(feeds) { observableFeeds.value = it }
    }

    fun addFeed(roomFeed: RoomFeed){
        repository.addFeed(roomFeed)
    }

    fun hideChange(roomFeed: RoomFeed){
        //repository.hideChange(roomFeed)
        addFeed(roomFeed)
    }

    fun deleteFeed(roomFeed: RoomFeed){
        repository.deleteFeed(roomFeed)
    }

    fun deleteAll(){
        repository.deleteAll()
    }
}