package cz.cvut.palecda1

import android.app.Application
import cz.cvut.palecda1.dao.ArticleDaoFake
import cz.cvut.palecda1.repository.AppDatabase
import cz.cvut.palecda1.repository.ArticleRepository
import cz.cvut.palecda1.repository.FeedRepository

object MyInjector {

    private var articleRepository: ArticleRepository? = null
    fun getArticleRepo(application: Application): ArticleRepository{
        if(articleRepository == null){
            synchronized(this){
                if(articleRepository == null){
                    articleRepository = ArticleRepository(ArticleDaoFake())
                    //articleRepository = ArticleRepository(AppDatabase.getInstance(application).articleDao())
                }
            }
        }
        return articleRepository!!
    }

    private var feedRepository: FeedRepository? = null
    fun getFeedRepo(application: Application): FeedRepository{
        if(feedRepository == null){
            synchronized(this){
                if(feedRepository == null){
                    feedRepository = FeedRepository(AppDatabase.getInstance(application).feedDao())
                }
            }
        }
        return feedRepository!!
    }
}