package cz.cvut.palecda1.dao

import cz.cvut.palecda1.article.Article

interface ArticleDao {
    fun articleList(): List<Article>
    fun articleById(id: Int): Article?

}