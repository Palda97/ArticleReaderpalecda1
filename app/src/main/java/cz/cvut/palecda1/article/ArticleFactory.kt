package cz.cvut.palecda1.article

import android.util.Log
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.model.RoomArticle
import cz.cvut.palecda1.model.RoomFeed
import cz.cvut.palecda1.view.HtmlFactory

import cz.cvut.palecda1.view.HtmlFactory.stripImg
import cz.cvut.palecda1.view.HtmlFactory.stripTags
import cz.cvut.palecda1.view.HtmlFactory.justify

class ArticleFactory {

    fun syndEntryList(syndEntryList: List<Pair<SyndEntry, RoomFeed>>): List<RoomArticle> = syndEntryList.map { syndEntry(it) }

    fun syndEntry(syndEntryPair: Pair<SyndEntry, RoomFeed>): RoomArticle {
        val syndEntry = syndEntryPair.first
        val url = syndEntry.link ?: "void"
        val title = syndEntry.title ?: ""
        @Suppress("UNCHECKED_CAST")
        val contents = syndEntry.contents as List<SyndContent>
        var body = contents.map { it.value }.joinToString(separator = "<br>")
        if (STRIP_IMGS)
            body = body.stripImg()
        //body = syndEntry.toString()
        val description = if(syndEntry.description != null && syndEntry.description.value != null)
            syndEntry.description.value
        else {
            var res: String
            val tmpDescription = body.stripTags()
            res = if(tmpDescription.length < MAX_CUSTOM_DESCRIPTION_LENGTH)
                tmpDescription
            else
                "${tmpDescription.substring(0, MAX_CUSTOM_DESCRIPTION_LENGTH -1)}..."
            if(CUSTOM_DESCRIPTION_TOKEN)
                res = "$CUSTOM_DESCRIPTION_TOKEN_VALUE$res"
            res
        }
        if(body.isEmpty()){
            body = description
            if(CUSTOM_BODY_TOKEN){
                body = "$CUSTOM_BODY_TOKEN_VALUE$body"
            }
        }
        val feed = syndEntryPair.second
        val feedTitle = feed.title
        val feedUrl = feed.url
        val date: String = syndEntry.publishedDate?.toString() ?: ""
        //Log.d(TAG, description)
        return RoomArticle(url, title, body, description, feedTitle, feedUrl, date)
    }

    companion object {
        const val MAX_CUSTOM_DESCRIPTION_LENGTH = 200
        const val STRIP_IMGS = true
        val CUSTOM_DESCRIPTION_TOKEN_VALUE = HtmlFactory.coloredText("[~] ", AppInit.injector.colorCustomTokens)
        const val CUSTOM_DESCRIPTION_TOKEN = true
        const val CUSTOM_BODY_TOKEN = true
        val CUSTOM_BODY_TOKEN_VALUE = HtmlFactory.coloredText("[just description again]<br>", AppInit.injector.colorCustomTokens)
        const val TAG = "ArticleFactory"
    }
}

