package cz.cvut.palecda1

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import cz.cvut.palecda1.Article.Article
import cz.cvut.palecda1.Article.DataStorage
import cz.cvut.palecda1.Article.MyStorage
import kotlinx.android.synthetic.main.text_field_in_layout.view.*

class ArticleFragment : Fragment() {

    var listener: ArticleFragmentListener? = null
    lateinit var pLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        /*val headline = Objects.requireNonNull<Bundle>(arguments).getString(ARG_HEADLINE)
        headlineTextView = view.findViewById(R.id.headline) as TextView
        headlineTextView.text = headline*/

        pLayout = view.findViewById(R.id.insert_articles_here) as LinearLayout

        showArticles(inflater, container, MyStorage.articleSupplier.arrayOfArticles())

        return view
    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is ArticleFragmentListener){
            listener = context
        }else{
            throw UnsupportedOperationException("Context is not ArticleFragmentListener!")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun showArticles(inflater: LayoutInflater, container: ViewGroup?, articles: Array<Article>){
        articles.forEachIndexed { index, s ->
            val lView = inflater.inflate(R.layout.text_field_in_layout, container)
            lView.textViewInLayout.text = Html.fromHtml(s.body)
            lView.textViewInLayout.setOnClickListener{listener?.showDetail(index)}
            pLayout.addView(lView, pLayout.childCount)
        }
    }


    /*
    fun changeHeadline(headline: String) {
        headlineTextView.text = headline
    }
    */

    interface ArticleFragmentListener {
        fun showDetail(id: Int)
    }

    /*fun showDetail(v: View){

    }*/

    companion object {
        //private const val ARG_HEADLINE = "headline"

        fun newInstance(/*headline: String*/): ArticleFragment {
            val fragment = ArticleFragment()
            /*val args = Bundle()
            args.putString(ARG_HEADLINE, headline)
            fragment.arguments = args*/
            return fragment
        }
    }

}