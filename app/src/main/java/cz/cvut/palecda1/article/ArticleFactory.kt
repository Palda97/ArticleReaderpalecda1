package cz.cvut.palecda1.article

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import cz.cvut.palecda1.model.RoomArticle

class ArticleFactory {

    fun syndEntryList(syndEntryList: List<SyndEntry>): List<RoomArticle> = syndEntryList.map { syndEntry(it) }

    fun syndEntry(syndEntry: SyndEntry): RoomArticle = RoomArticle(syndEntry.uri, syndEntry.title,syndEntry.description.value)
}

