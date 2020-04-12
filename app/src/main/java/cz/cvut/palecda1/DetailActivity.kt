package cz.cvut.palecda1
//androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.cvut.palecda1.article.Article

class DetailActivity : AppCompatActivity() {

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getIntExtra(ID, 0)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container_detail_fragment, DetailFragment.newInstance(id))
                .commit()
        }
    }

    companion object{
        const val ID = "ID"

        fun start(act: AppCompatActivity, article: Article){
            val intent = Intent(act, DetailActivity::class.java)
            intent.putExtra(ID, article.address)
            act.startActivity(intent)
        }
    }
}
