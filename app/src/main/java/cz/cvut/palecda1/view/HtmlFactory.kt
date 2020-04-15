package cz.cvut.palecda1.view

import android.text.Spanned
import androidx.core.text.HtmlCompat
import cz.cvut.palecda1.model.RoomArticle

object HtmlFactory {
    fun toHtml(article: RoomArticle): Spanned {
        return HtmlCompat.fromHtml("<h3>${article.title}</h3><a href=\"${article.url}\">${article.url}</a><br>${article.body}", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}