package cz.cvut.palecda1

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import cz.cvut.palecda1.Article.DataStorage
import cz.cvut.palecda1.Article.MyStorage
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var articleTextView: TextView
    private var myid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        articleTextView = view.findViewById(R.id.textViewForArticle) as TextView

        myid = Objects.requireNonNull<Bundle>(arguments).getInt(ID)
        showArticle()

        return view
    }

    fun showArticle(){
        articleTextView.text = Html.fromHtml(getBody())
    }
    fun getBody(): String{
        return MyStorage.articleSupplier.articleById(myid).body
    }
    fun getAddress(): String{
        return MyStorage.articleSupplier.articleById(myid).address
    }



    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.my_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            return when (item.itemId) {
                R.id.shareItem -> {
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.setType(TEXT_PLAIN)
                    val body = getAddress()
                    //val subject = ""
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, body)
                    //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }else
            return super.onOptionsItemSelected(item)
    }

    /*override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is DetailFragmentListener){
            listener = context
        }else{
            throw UnsupportedOperationException("Context is not DetailFragmentListener!")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface DetailFragmentListener {
        fun getAc(): AppCompatActivity
    }*/

    companion object {

        private const val ID = "ID"
        private const val TEXT_PLAIN = "text/plain"

        fun newInstance(number: Int): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putInt(ID, number)
            fragment.arguments = args
            return fragment
        }
    }

}