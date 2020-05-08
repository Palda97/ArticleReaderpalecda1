package cz.cvut.palecda1.dao

import android.util.Log
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader
import cz.cvut.palecda1.MyInjector
import cz.cvut.palecda1.article.ArticleFactory
import cz.cvut.palecda1.model.RoomArticle
import cz.cvut.palecda1.model.RoomFeed
import java.net.URL

class ArticleDaoRome : ArticleDao {
    val feedDao = MyInjector.getFeedRepo().feedDao

    override fun articleList(): List<RoomArticle> {
        val listOfFeeds = feedDao.activeFeedsOnly()
        val listOfSyndFeeds = feedsToSyndFeeds(listOfFeeds)
        val listOfSyndEntries = syndFeedsToSyndEntries(listOfSyndFeeds)
        return ArticleFactory().syndEntryList(listOfSyndEntries)
    }

    /*private fun syndFeedsToSyndEntries(listOfSyndFeeds: List<SyndFeed>): List<SyndEntry> {
        val listOfSyndEntries = mutableListOf<SyndEntry>()
        listOfSyndFeeds.forEach {
            @Suppress("UNCHECKED_CAST")
            listOfSyndEntries.addAll(it.entries.toList() as List<SyndEntry>)
        }
        return listOfSyndEntries
    }*/
    private fun meltSyndEntryLists(list: MutableList<List<SyndEntry>>): List<SyndEntry> {
        fun melt(list: MutableList<List<SyndEntry>>, level: Int, acc: MutableList<SyndEntry>): MutableList<SyndEntry> {
            if (list.isEmpty())
                return acc
            val newList = mutableListOf<List<SyndEntry>>()
            list.forEach {
                if(it.size > level){
                    newList.add(it)
                    acc.add(it[level])
                }
            }
            return melt(newList, level +1, acc)
        }
        return melt(list, 0, mutableListOf<SyndEntry>())
    }
    private fun syndFeedsToSyndEntries(listOfSyndFeeds: List<SyndFeed>): List<SyndEntry> {
        val listOfLists = mutableListOf<List<SyndEntry>>()
        listOfSyndFeeds.forEach {
            @Suppress("UNCHECKED_CAST")
            listOfLists.add(it.entries.toList() as List<SyndEntry>)
        }
        return meltSyndEntryLists(listOfLists)
    }

    private fun feedsToSyndFeeds(listOfFeeds: List<RoomFeed>): List<SyndFeed> {
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

    override fun deleteAll() {
        TODO("not implemented")
    }

    override fun insertArticles(list: List<RoomArticle>) {
        TODO("not implemented")
    }

    override fun clearAndInsertList(list: List<RoomArticle>) {
        TODO("not implemented")
    }

    companion object {
        const val TAG = "ArticleDaoRome"
    }
}