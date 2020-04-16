package cz.cvut.palecda1.article

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import cz.cvut.palecda1.model.RoomArticle

class ArticleFactory {

    fun syndEntryList(syndEntryList: List<SyndEntry>): List<RoomArticle> = syndEntryList.map { syndEntry(it) }

    fun syndEntry(syndEntry: SyndEntry): RoomArticle {
        val url = syndEntry.uri ?: "void"
        val title = syndEntry.title ?: ""
        val description = if(syndEntry.description != null && syndEntry.description.value != null) syndEntry.description.value else url
        val body = (syndEntry.contents as List<SyndContent>)[0].value
        return RoomArticle(url, title, body, description)
    }
}

