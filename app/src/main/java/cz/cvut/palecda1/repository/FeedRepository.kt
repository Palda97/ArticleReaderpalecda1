package cz.cvut.palecda1.repository

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
        val list = feedDao.feedList()
        data.value = MailPackage(list, MailPackage.OK, "")
        return data
    }

    fun addFeed(roomFeed: RoomFeed){
        feedDao.insertFeed(roomFeed)
    }
}