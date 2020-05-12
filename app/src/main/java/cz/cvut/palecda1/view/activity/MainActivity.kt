package cz.cvut.palecda1.view.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.view.fragment.ArticleFragment
import cz.cvut.palecda1.R
import cz.cvut.palecda1.model.RoomArticle

class MainActivity : AppCompatActivity(),
    ArticleFragment.ArticleFragmentListener {

    override fun showDetail(roomArticle: RoomArticle) {
        if(isLand){
            val repository = AppInit.injector.getArticleRepo(this)
            repository.getArticleById(roomArticle.url)
        } else {
            DetailActivity.start(this@MainActivity, roomArticle)
        }
    }

    override fun configFeeds() {
        FeedActivity.start(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(isLand)
            menuInflater.inflate(R.menu.menu_landscape, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.detailItem -> {
                val article: RoomArticle? = AppInit.injector.getArticleRepo(this).observableArticle.value?.mailContent
                if(article == null){
                    Toast.makeText(this, getString(R.string.no_article_selected), Toast.LENGTH_SHORT).show()
                    return true
                }
                DetailActivity.start(this, article)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val isLand: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}
