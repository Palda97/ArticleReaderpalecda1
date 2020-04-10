package cz.cvut.palecda1.article

interface ArticleSupplier {
    //fun listOfArticles(): List<Article>
    fun arrayOfArticles(): Array<Article>
    fun articleById(id: Int): Article
}