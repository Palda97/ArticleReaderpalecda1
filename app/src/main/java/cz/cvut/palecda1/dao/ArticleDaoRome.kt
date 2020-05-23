package cz.cvut.palecda1.dao

import android.util.Log
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.article.ArticleFactory
import cz.cvut.palecda1.model.RoomArticle
import cz.cvut.palecda1.model.RoomFeed
import java.net.URL

class ArticleDaoRome : ArticleDao {
    val feedDao = AppInit.injector.getFeedRepo().feedDao

    override fun articleList(): List<RoomArticle> {
        val listOfFeeds = feedDao.activeFeedsOnly()
        val listOfSyndFeeds = feedsToSyndFeeds(listOfFeeds)
        val listOfSyndEntries = syndFeedsToSyndEntries(listOfSyndFeeds, listOfFeeds)
        return ArticleFactory().syndEntryList(listOfSyndEntries).reversed()
    }

    override fun articleListNotHiding(): List<RoomArticle> {
        TODO("not implemented")
    }

    /*private fun syndFeedsToSyndEntries(listOfSyndFeeds: List<SyndFeed>): List<SyndEntry> {
        val listOfSyndEntries = mutableListOf<SyndEntry>()
        listOfSyndFeeds.forEach {
            @Suppress("UNCHECKED_CAST")
            listOfSyndEntries.addAll(it.entries.toList() as List<SyndEntry>)
        }
        return listOfSyndEntries
    }*/
    private fun meltSyndEntryLists(list: MutableList<Pair<List<SyndEntry>, RoomFeed>>): List<Pair<SyndEntry, RoomFeed>> {
        tailrec fun melt(list: MutableList<Pair<List<SyndEntry>, RoomFeed>>, level: Int, acc: MutableList<Pair<SyndEntry, RoomFeed>>): MutableList<Pair<SyndEntry, RoomFeed>> {
            if (list.isEmpty())
                return acc
            val newList = mutableListOf<Pair<List<SyndEntry>, RoomFeed>>()
            list.forEach {
                if(it.first.size > level){
                    newList.add(it)
                    acc.add(Pair(it.first[level], it.second))
                }
            }
            return melt(newList, level +1, acc)
        }
        return melt(list, 0, mutableListOf<Pair<SyndEntry, RoomFeed>>())
    }
    private fun syndFeedsToSyndEntries(listOfSyndFeeds: List<SyndFeed>, listOfFeeds: List<RoomFeed>): List<Pair<SyndEntry, RoomFeed>> {
        val listOfLists = mutableListOf<Pair<List<SyndEntry>, RoomFeed>>()
        listOfSyndFeeds.forEachIndexed {i, it ->
            @Suppress("UNCHECKED_CAST")
            listOfLists.add(Pair(it.entries.toList() as List<SyndEntry>, listOfFeeds[i]))
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

    override fun clearAndInsertList(list: List<RoomArticle>, delete: Boolean): List<RoomArticle> {
        TODO("not implemented")
    }

    companion object {
        const val TAG = "ArticleDaoRome"
    }
}