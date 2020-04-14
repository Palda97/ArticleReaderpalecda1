package cz.cvut.palecda1.article

import cz.cvut.palecda1.model.RoomArticle

interface ArticleSupplier {
    fun arrayOfArticles(): Array<RoomArticle>
    fun articleById(id: Int): RoomArticle
}