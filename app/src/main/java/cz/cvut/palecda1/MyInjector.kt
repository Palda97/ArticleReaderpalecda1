package cz.cvut.palecda1

import cz.cvut.palecda1.dao.ArticleDaoFake
import cz.cvut.palecda1.dao.FeedDaoFake
import cz.cvut.palecda1.dao.FeedDaoRoom
import cz.cvut.palecda1.repository.ArticleRepository
import cz.cvut.palecda1.repository.FeedRepository
import cz.cvut.palecda1.viewmodel.ArticleViewModelFactory
import cz.cvut.palecda1.viewmodel.FeedViewModelFactory

object MyInjector {
    val ARTICLE_VIEW_MODEL_FACTORY = ArticleViewModelFactory(ArticleRepository(ArticleDaoFake()))
    val FEED_VIEW_MODEL_FACTORY = FeedViewModelFactory(FeedRepository(FeedDaoFake()))
}