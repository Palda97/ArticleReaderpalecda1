package cz.cvut.palecda1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), ArticleFragment.ArticleFragmentListener {
    override fun showDetail(id: Int) {
        DetailActivity.start(this@MainActivity, id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
