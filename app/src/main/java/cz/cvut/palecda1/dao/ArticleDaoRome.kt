package cz.cvut.palecda1.dao

import android.util.Log
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader
import cz.cvut.palecda1.MyInjector
import cz.cvut.palecda1.article.ArticleFactory
import cz.cvut.palecda1.model.RoomArticle
import cz.cvut.palecda1.model.RoomFeed
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class ArticleDaoRome : ArticleDao {
    val feedDao = MyInjector.getFeedRepo().feedDao

    override fun articleList(): List<RoomArticle> {
        val listOfFeeds = feedDao.feedList()
        val listOfSyndFeeds = feedsToSyndFeeds(listOfFeeds)
        val listOfSyndEntries = syndFeedsToSyndEntries(listOfSyndFeeds)
        return ArticleFactory().syndEntryList(listOfSyndEntries)
    }

    fun syndFeedsToSyndEntries(listOfSyndFeeds: List<SyndFeed>): List<SyndEntry> {
        val listOfSyndEntries = mutableListOf<SyndEntry>()
        listOfSyndFeeds.forEach {
            @Suppress("UNCHECKED_CAST")
            listOfSyndEntries.addAll(it.entries.toList() as List<SyndEntry>)
        }
        return listOfSyndEntries
    }

    fun feedsToSyndFeeds(listOfFeeds: List<RoomFeed>): List<SyndFeed> {
        val listOfSyndFeeds = mutableListOf<SyndFeed>()
        listOfFeeds.forEach {
            val url: URL = URL(it.url)
            val reader = XmlReader(url)
            val syndFeed: SyndFeed = SyndFeedInput().build(reader)
            listOfSyndFeeds.add(syndFeed)
        }
        return listOfSyndFeeds
    }

    override fun articleById(url: String): RoomArticle? {
        Log.d(TAG, "yikes")
        val list = articleList()
        list.forEach {
            if (it.url == url)
                return it
        }
        return null
    }

    companion object {
        const val TAG = "ArticleDaoRome"
    }
}