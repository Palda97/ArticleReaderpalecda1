package cz.cvut.palecda1.repository

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.dao.FeedDao
import cz.cvut.palecda1.model.RoomFeed

class FeedRepository(val feedDao: FeedDao) {

    //val articleRepository = AppInit.injector.getArticleRepo()

    val observableFeeds: MediatorLiveData<MailPackage<List<RoomFeed>>> = MediatorLiveData()

    fun getFeedList(): LiveData<MailPackage<List<RoomFeed>>> {
        val data = MutableLiveData<MailPackage<List<RoomFeed>>>()
        data.value = MailPackage.loadingPackage()
        /*val list = feedDao.feedList()
        data.value = MailPackage(list, MailPackage.OK, "")*/
        asyncGetFeeds(data)
        observableFeeds.addSource(data) { observableFeeds.value = it }
        return data
    }

    fun addFeed(feed: RoomFeed) {
        doAsync { feedDao.insertFeed(feed) }
    }

    /*fun hideChange(feed: RoomFeed) {
        doAsync {
            feedDao.insertFeed(feed)
            articleRepository?.getArticleList()
        }
    }*/

    fun deleteFeed(feed: RoomFeed) {
        doAsync { feedDao.deleteFeed(feed) }
    }

    fun deleteAll(){
        doAsync { feedDao.deleteAll() }
    }

    fun activeOnly(): LiveData<MailPackage<List<RoomFeed>>> {
        val data = MutableLiveData<MailPackage<List<RoomFeed>>>()
        data.value = MailPackage.loadingPackage()
        asyncGetFeeds(data, true)
        observableFeeds.addSource(data) { observableFeeds.value = it }
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
    fun asyncGetFeeds(data: MutableLiveData<MailPackage<List<RoomFeed>>>, activeOnly: Boolean = false) {
        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                //Thread.sleep(2000)
                val list = if(activeOnly)
                    feedDao.activeFeedsOnly()
                else
                    feedDao.feedList()
                data.postValue(
                    MailPackage(
                        list,
                        MailPackage.OK,
                        ""
                    )
                )
                return null
            }
        }.execute()
    }
}