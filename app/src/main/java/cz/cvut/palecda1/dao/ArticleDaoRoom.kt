package cz.cvut.palecda1.dao

import androidx.room.Dao
import androidx.room.Query
import cz.cvut.palecda1.article.Article

@Dao
abstract class ArticleDaoRoom : ArticleDao{

    @Query("SELECT * from roomarticle")
    abstract override fun articleList(): List<Article>

    @Query("SELECT * from roomarticle WHERE url = :url")
    abstract fun articleByIdSafe(url: String): List<Article>

    override fun articleById(url: String): Article? {
        val list = articleByIdSafe(url)
        return if(list.size == 1){
            list[0]
        }else{
            null
        }
    }

    @Query("delete from roomarticle")
    abstract fun deleteAll()
}