package cz.cvut.palecda1.dao

import cz.cvut.palecda1.model.RoomArticle

interface ArticleDao {
    fun articleList(): List<RoomArticle>
    fun articleById(url: String): RoomArticle?
}