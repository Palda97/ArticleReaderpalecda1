package cz.cvut.palecda1.dao

import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.DataStorage

class ArticleDaoFake : ArticleDao {
    val dataStorage = DataStorage()

    override fun articleList(): List<Article> = dataStorage.arrayOfArticles().toList()

    override fun articleById(id: Int): Article? {
        return if(id < dataStorage.articleArray.size && id >= 0)
            dataStorage.articleById(id)
        else
            null
    }
}