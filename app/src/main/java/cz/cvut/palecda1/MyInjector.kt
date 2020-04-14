package cz.cvut.palecda1

import cz.cvut.palecda1.dao.ArticleDaoFake
import cz.cvut.palecda1.repository.ArticleRepository
import cz.cvut.palecda1.viewmodel.ArticleViewModelFactory

object MyInjector {
    val ARTICLE_VIEW_MODEL_FACTORY = ArticleViewModelFactory(ArticleRepository(ArticleDaoFake()))
}