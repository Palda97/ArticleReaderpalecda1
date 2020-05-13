package cz.cvut.palecda1

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import cz.cvut.palecda1.dao.ArticleDaoRome
import cz.cvut.palecda1.repository.AppDatabase
import cz.cvut.palecda1.repository.ArticleRepository
import cz.cvut.palecda1.repository.FeedRepository

class Injector (val db: AppDatabase, val colorFakeLinks: String, val colorCustomTokens: String) {

    private var articleRepository: ArticleRepository? = null
    fun getArticleRepo(context: Context): ArticleRepository {
        if (articleRepository == null) {
            synchronized(this) {
                if (articleRepository == null) {
                    articleRepository = ArticleRepository(db.articleDao(), ArticleDaoRome(), context)
                    //articleRepository = ArticleRepository(db.articleDao(), ArticleDaoFake(), application)
                }
            }
        }
        return articleRepository!!
    }

    fun getArticleRepo(): ArticleRepository? = articleRepository

    private var feedRepository: FeedRepository? = null
    fun getFeedRepo(): FeedRepository {
        if (feedRepository == null) {
            synchronized(this) {
                if (feedRepository == null) {
                    feedRepository = FeedRepository(db.feedDao())
                    //feedRepository = FeedRepository(FeedDaoFake())
                }
            }
        }
        return feedRepository!!
    }

    companion object {
        fun generate(context: Context): Injector {
            val db = AppDatabase.getInstance(context)
            val colorFakeLinks: Int = ContextCompat.getColor(context, R.color.colorFakeLinks)
            val colorCustomTokens: Int = ContextCompat.getColor(context, R.color.colorCustomTokens)
            return Injector(db, AppInit.colorToHexString(colorFakeLinks), AppInit.colorToHexString(colorCustomTokens))
        }
        fun noArticleSelectedToast(context: Context) = Toast.makeText(context, context.resources.getString(R.string.no_article_selected), Toast.LENGTH_SHORT).show()
    }
}