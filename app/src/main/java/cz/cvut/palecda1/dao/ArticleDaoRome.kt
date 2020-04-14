package cz.cvut.palecda1.dao

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader
import cz.cvut.palecda1.MyInjector
import cz.cvut.palecda1.article.ArticleFactory
import cz.cvut.palecda1.model.RoomArticle
import java.net.URL

class ArticleDaoRome: ArticleDao {
    val feedDao = MyInjector.getFeedRepo().feedDao

    override fun articleList(): List<RoomArticle> {
        val listOfFeeds = feedDao.feedList()
        val listOfSyndFeeds = mutableListOf<SyndFeed>()
        listOfFeeds.forEach{
            val url: URL = URL(it.url)
            val reader = XmlReader(url)
            val syndFeed: SyndFeed = SyndFeedInput().build(reader)
            listOfSyndFeeds.add(syndFeed)
        }
        val listOfSyndEntries = mutableListOf<SyndEntry>()
        listOfSyndFeeds.forEach{
            @Suppress("UNCHECKED_CAST")
            listOfSyndEntries.addAll(it.entries.toList() as List<SyndEntry>)
        }
        return ArticleFactory().syndEntryList(listOfSyndEntries)
    }

    override fun articleById(url: String): RoomArticle? {
        val uri: URL = URL(url)
        val reader = XmlReader(uri)
        TODO("articleById")
    }
}