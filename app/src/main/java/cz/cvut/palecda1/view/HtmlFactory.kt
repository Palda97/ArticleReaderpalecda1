package cz.cvut.palecda1.view

import android.text.Spanned
import androidx.core.text.HtmlCompat
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.model.RoomArticle

object HtmlFactory {
    fun toHtml(title: String, body: String): Spanned {
        //val text = "<h3>${article.title}</h3><a href=\"${article.url}\">${article.url}</a><br>${article.body}"
        val text = "<h3>${title}</h3>${body}"
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
    fun toHtml(title: String, body: String, feed: String): Spanned {
        //val text = "<h3>${title}</h3><font color=\"${MyInjector.COLOR_FAKE_LINKS}\">[$feed]</font><br>${body}"
        //val text = "<h3>${title}</h3><font color=\"${AppInit.injector.colorFakeLinks}\">[$feed]</font><br>${body}"
        val text = "<h3>${title}</h3>${coloredText("[$feed]", AppInit.injector.colorFakeLinks)}<br>${body}"
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    //private const val IMG_REPLACEMENT = "&lt;img&gt;"
    private const val IMG_REPLACEMENT = ""
    fun String.stripImg(): String {
        return this.replace(Regex("<img [^>]*>"), IMG_REPLACEMENT)
    }

    private val LINK_REPLACEMENT = arrayOf("<p style=\"color:${AppInit.injector.colorFakeLinks}\">", "</p>")
    fun String.stripLinks(): String {
        require(LINK_REPLACEMENT.size == 2)
        return this
            .replace(Regex("<a [^>]*>"), LINK_REPLACEMENT[0])
            .replace(Regex("</a>"), LINK_REPLACEMENT[1])
    }

    fun String.removeTags(except: String? = null): String {
        var tmp = ""
        if(except != null)
            tmp = "[^$except]"
        val regex = "</?$tmp[^>]*>"
        return this.replace(Regex(regex),"" )
    }

    fun String.stripTags(except: String? = null): String {
        return this
            .stripImg()
            .stripLinks()
            .removeTags(except)
    }

    fun coloredText(text: String, color: String): String = "<font color=\"${color}\">${text}</font>"
}