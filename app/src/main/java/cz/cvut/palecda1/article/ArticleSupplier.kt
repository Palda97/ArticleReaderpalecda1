package cz.cvut.palecda1.article

interface ArticleSupplier {
    fun arrayOfArticles(): Array<Article>
    fun articleById(id: Int): Article
}