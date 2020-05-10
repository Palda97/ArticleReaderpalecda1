package cz.cvut.palecda1

import android.app.Application
import cz.cvut.palecda1.dao.ArticleDaoRome
import cz.cvut.palecda1.repository.AppDatabase
import cz.cvut.palecda1.repository.ArticleRepository
import cz.cvut.palecda1.repository.FeedRepository

class Injector(val db: AppDatabase, val colorFakeLinks: String, val colorCustomTokens: String) {

    private var articleRepository: ArticleRepository? = null
    fun getArticleRepo(application: Application): ArticleRepository {
        if (articleRepository == null) {
            synchronized(this) {
                if (articleRepository == null) {
                    articleRepository = ArticleRepository(db.articleDao(), ArticleDaoRome(), application)
                    //articleRepository = ArticleRepository(db.articleDao(), ArticleDaoFake(), application)
                }
            }
        }
        return articleRepository!!
    }

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
}