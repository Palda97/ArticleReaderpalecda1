package cz.cvut.palecda1.dao

import androidx.room.Dao
import androidx.room.Query
import cz.cvut.palecda1.article.Article

@Dao
abstract class ArticleDaoRoom : ArticleDao{

    @Query("SELECT * from roomarticle")
    abstract override fun articleList(): List<Article>

    @Query("SELECT * from roomarticle WHERE lol = 0")
    abstract fun articleByIdSafe(id: Int): List<Article>

    override fun articleById(id: Int): Article? {
        val list = articleByIdSafe(id)
        return if(list.size == 1){
            list[0]
        }else{
            null
        }
    }
}