package cz.cvut.palecda1.view.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import cz.cvut.palecda1.view.fragment.DetailFragment
import cz.cvut.palecda1.R
import cz.cvut.palecda1.model.RoomArticle

class DetailActivity : AppCompatActivity() {

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getStringExtra(ID) ?: ""

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.container_detail_fragment,
                    DetailFragment.newInstance(id)
                )
                .commit()
        }
    }

    private val isLand: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(isLand)
            menuInflater.inflate(R.menu.menu_detail_landscape, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.cancelDetailItem -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object{
        const val ID = "ID"

        fun start(act: AppCompatActivity, roomArticle: RoomArticle){
            val intent = Intent(act, DetailActivity::class.java)
            intent.putExtra(ID, roomArticle.url)
            act.startActivity(intent)
        }
    }
}
