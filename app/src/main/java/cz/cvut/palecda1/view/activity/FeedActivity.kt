package cz.cvut.palecda1.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.cvut.palecda1.view.fragment.FeedFragment
import cz.cvut.palecda1.R

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.container_feed_fragment,
                    FeedFragment.newInstance()
                )
                .commit()
        }
    }

    companion object{
        //const val ID = "ID"

        fun start(act: AppCompatActivity){
            val intent = Intent(act, FeedActivity::class.java)
            //intent.putExtra(ID, article.url)
            act.startActivity(intent)
        }
    }
}
