package cz.cvut.palecda1.dao

import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.DataStorage

class ArticleDaoFake : ArticleDao {
    val dataStorage = DataStorage()
    val mapByUrl: MutableMap<String, Article> = HashMap()

    init {
        dataStorage.articleArray.forEach {
            mapByUrl[it.address] = it
        }
    }

    override fun articleList(): List<Article> = dataStorage.arrayOfArticles().toList()

    override fun articleById(id: String): Article? = mapByUrl[id]
}