package cz.cvut.palecda1

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import cz.cvut.palecda1.article.MyStorage
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

    private fun showArticle(){
        //articleTextView.text = Html.fromHtml(getBody())
        articleTextView.text = HtmlCompat.fromHtml(getBody(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
    private fun getBody(): String{
        return MyStorage.ARTICLE_SUPPLIER.articleById(myid).body
    }
    private fun getAddress(): String{
        return MyStorage.ARTICLE_SUPPLIER.articleById(myid).address
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
                    sharingIntent.type = TEXT_PLAIN
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