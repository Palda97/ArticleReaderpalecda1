package cz.cvut.palecda1.repository

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.MailPackage
import cz.cvut.palecda1.dao.ArticleDao
import cz.cvut.palecda1.dao.ArticleDaoFake
import cz.cvut.palecda1.dao.FeedDao
import cz.cvut.palecda1.model.RoomFeed

class FeedRepository(private val feedDao: FeedDao) {

    fun getFeedList(): LiveData<MailPackage<List<RoomFeed>>> {
        val data = MutableLiveData<MailPackage<List<RoomFeed>>>()
        data.value = MailPackage(null, MailPackage.LOADING, "")
        /*val list = feedDao.feedList()
        data.value = MailPackage(list, MailPackage.OK, "")*/
        asyncGetFeeds(data)
        return data
    }

    /*fun addFeed(roomFeed: RoomFeed){
        feedDao.insertFeed(roomFeed)
    }*/

    fun addFeed(feed: RoomFeed) {
        doAsync { feedDao.insertFeed(feed) }
    }

    fun deleteFeed(feed: RoomFeed) {
        doAsync { feedDao.deleteFeed(feed) }
    }

    init {
        //doAsync { feedDao.deleteAll() }
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
    fun asyncGetFeeds(data: MutableLiveData<MailPackage<List<RoomFeed>>>) {
        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                Thread.sleep(2000)
                val list = feedDao.feedList()
                data.postValue(MailPackage(list, MailPackage.OK, ""))
                return null
            }
        }.execute()
    }
}