package cz.cvut.palecda1.view.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.cvut.palecda1.AppInit
import cz.cvut.palecda1.view.fragment.ArticleFragment
import cz.cvut.palecda1.R
import cz.cvut.palecda1.model.RoomArticle

class MainActivity : AppCompatActivity(),
    ArticleFragment.ArticleFragmentListener {
    override fun showDetail(roomArticle: RoomArticle) {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
}
