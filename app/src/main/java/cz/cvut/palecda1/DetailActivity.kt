package cz.cvut.palecda1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.cvut.palecda1.model.RoomArticle

class DetailActivity : AppCompatActivity() {

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getStringExtra(ID) ?: ""

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container_detail_fragment, DetailFragment.newInstance(id))
                .commit()
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
