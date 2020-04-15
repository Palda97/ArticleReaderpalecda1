package cz.cvut.palecda1.dao

import androidx.room.*
import cz.cvut.palecda1.model.RoomArticle

@Dao
abstract class ArticleDaoRoom : ArticleDao{

    @Query("SELECT * from roomarticle")
    abstract override fun articleList(): List<RoomArticle>

    @Query("SELECT * from roomarticle WHERE url = :url")
    abstract fun articleByIdSafe(url: String): List<RoomArticle>

    override fun articleById(url: String): RoomArticle? {
        val list = articleByIdSafe(url)
        return if(list.size == 1){
            list[0]
        }else{
            null
        }
    }

    @Query("delete from roomarticle")
    abstract override fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertArticles(list: List<RoomArticle>)

    @Transaction
    override fun clearAndInsertList(list: List<RoomArticle>) {
        deleteAll()
        insertArticles(list)
    }
}