package cz.cvut.palecda1.dao

import cz.cvut.palecda1.model.RoomArticle
import java.util.*
import kotlin.collections.HashMap

class ArticleDaoFake: ArticleDao {

    val articleMap: MutableMap<String, RoomArticle> = HashMap()
    init {
        for(it in 0 until 10){
            val currentTime = Calendar.getInstance().time.toString()
            val roomArticle = RoomArticle("https://www.example.com/article/$it/", "Article $it", "$currentTime<br>${LOREM}")
            articleMap[roomArticle.url] = roomArticle
        }
    }

    override fun articleList(): List<RoomArticle> {
        return articleMap.values.toList()
    }

    override fun articleById(url: String): RoomArticle? {
        return articleMap[url]
    }

    override fun deleteAll() {
        articleMap.clear()
    }

    override fun insertArticles(list: List<RoomArticle>) {
        list.forEach {
            articleMap[it.url] = it
        }
    }

    override fun clearAndInsertList(list: List<RoomArticle>) {
        deleteAll()
        insertArticles(list)
    }

    companion object {
        private const val LOREM =
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Mauris metus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Nulla accumsan, elit sit amet varius semper, nulla mauris mollis quam, tempor suscipit diam nulla vel leo. Etiam sapien elit, consequat eget, tristique non, venenatis quis, ante. Aliquam erat volutpat. Fusce consectetuer risus a nunc. Praesent in mauris eu tortor porttitor accumsan. Nullam eget nisl. Maecenas fermentum, sem in pharetra pellentesque, velit turpis volutpat ante, in pharetra metus odio a lectus. Nunc auctor.\n" +
                    "\n" +
                    "In convallis. Sed ac dolor sit amet purus malesuada congue. Morbi scelerisque luctus velit. Praesent in mauris eu tortor porttitor accumsan. Donec quis nibh at felis congue commodo. Vestibulum erat nulla, ullamcorper nec, rutrum non, nonummy ac, erat. Morbi scelerisque luctus velit. Maecenas lorem. Nullam justo enim, consectetuer nec, ullamcorper ac, vestibulum in, elit. Praesent in mauris eu tortor porttitor accumsan. Nulla quis diam. Duis sapien nunc, commodo et, interdum suscipit, sollicitudin et, dolor. Mauris metus. Praesent vitae arcu tempor neque lacinia pretium. Nullam at arcu a est sollicitudin euismod. Nulla non arcu lacinia neque faucibus fringilla. Aenean id metus id velit ullamcorper pulvinar. Duis bibendum, lectus ut viverra rhoncus, dolor nunc faucibus libero, eget facilisis enim ipsum id lacus."
    }
}