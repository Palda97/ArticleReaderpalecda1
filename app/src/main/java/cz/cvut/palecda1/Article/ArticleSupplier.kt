package cz.cvut.palecda1.Article

interface ArticleSupplier {
    //fun listOfArticles(): List<Article>
    fun arrayOfArticles(): Array<Article>
    fun articleById(id: Int): Article
}