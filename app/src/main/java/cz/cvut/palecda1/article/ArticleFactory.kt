package cz.cvut.palecda1.article

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import cz.cvut.palecda1.model.RoomArticle

import cz.cvut.palecda1.view.HtmlFactory.stripImg
import cz.cvut.palecda1.view.HtmlFactory.stripTags

class ArticleFactory {

    fun syndEntryList(syndEntryList: List<Pair<SyndEntry, String>>): List<RoomArticle> = syndEntryList.map { syndEntry(it) }

    fun syndEntry(syndEntryPair: Pair<SyndEntry, String>): RoomArticle {
        val syndEntry = syndEntryPair.first
        val url = syndEntry.link ?: "void"
        val title = syndEntry.title ?: ""
        @Suppress("UNCHECKED_CAST")
        val contents = syndEntry.contents as List<SyndContent>
        var body = contents.map { it.value }.joinToString(separator = "<br>")
        if (STRIP_IMGS)
            body = body.stripImg()
        //val body = syndEntry.toString()
        val description = if(syndEntry.description != null && syndEntry.description.value != null)
            syndEntry.description.value
        else {
            val tmpDescription = body.stripTags()
            if(tmpDescription.length < MAX_CUSTOM_DESCRIPTION_LENGTH)
                tmpDescription
            else
                "${tmpDescription.substring(0, MAX_CUSTOM_DESCRIPTION_LENGTH -1)}..."
        }
        val feed = syndEntryPair.second
        return RoomArticle(url, title, body, description, feed)
    }

    companion object{
        const val MAX_CUSTOM_DESCRIPTION_LENGTH = 200
        const val STRIP_IMGS = true
    }
}

