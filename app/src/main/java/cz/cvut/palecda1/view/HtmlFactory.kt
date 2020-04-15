package cz.cvut.palecda1.view

import android.text.Spanned
import androidx.core.text.HtmlCompat
import cz.cvut.palecda1.model.RoomArticle

object HtmlFactory {
    fun toHtml(article: RoomArticle): Spanned {
        //val text = "<h3>${article.title}</h3><a href=\"${article.url}\">${article.url}</a><br>${article.body}"
        val text = "<h3>${article.title}</h3>${article.body}"
        return HtmlCompat.fromHtml(stripImg(text), HtmlCompat.FROM_HTML_MODE_LEGACY)
        //return text
    }
    private fun stripImg(text: String): String {
        return text.replace(Regex("<img [^>]*>"), "&lt;img&gt;")
    }
}