package cz.cvut.palecda1
//androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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

        fun start(act: AppCompatActivity, number: Int){
            val intent = Intent(act, DetailActivity::class.java)
            intent.putExtra(ID, number)
            act.startActivity(intent)
        }
    }
}
