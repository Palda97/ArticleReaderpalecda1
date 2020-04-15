package cz.cvut.palecda1

import android.app.Application
import cz.cvut.palecda1.dao.ArticleDaoFake
import cz.cvut.palecda1.dao.ArticleDaoRome
import cz.cvut.palecda1.dao.FeedDaoFake
import cz.cvut.palecda1.repository.AppDatabase
import cz.cvut.palecda1.repository.ArticleRepository
import cz.cvut.palecda1.repository.FeedRepository

object MyInjector {

    val db: AppDatabase
        get() = AppInit.db

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