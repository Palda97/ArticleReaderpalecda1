package cz.cvut.palecda1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.cvut.palecda1.model.RoomArticle

class MainActivity : AppCompatActivity(), ArticleFragment.ArticleFragmentListener {
    override fun showDetail(roomArticle: RoomArticle) {
        DetailActivity.start(this@MainActivity, roomArticle)
    }

    override fun configFeeds() {
        FeedActivity.start(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
