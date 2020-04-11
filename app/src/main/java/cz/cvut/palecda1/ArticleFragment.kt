package cz.cvut.palecda1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.article.MyStorage
import kotlinx.android.synthetic.main.text_field_in_layout.view.*

class ArticleFragment : Fragment() {

    var listener: ArticleFragmentListener? = null
    lateinit var pLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)

        pLayout = view.findViewById(R.id.insert_articles_here) as LinearLayout

        showArticles(inflater, container, MyStorage.ARTICLE_SUPPLIER.arrayOfArticles())

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

    private fun showArticles(inflater: LayoutInflater, container: ViewGroup?, articles: Array<Article>){
        articles.forEachIndexed { index, s ->
            val lView = inflater.inflate(R.layout.text_field_in_layout, container)
            //lView.textViewInLayout.text = Html.fromHtml(s.body)
            lView.textViewInLayout.text = HtmlCompat.fromHtml(s.body, HtmlCompat.FROM_HTML_MODE_LEGACY)
            lView.textViewInLayout.setOnClickListener{listener?.showDetail(index)}
            pLayout.addView(lView, pLayout.childCount)
        }
    }

    interface ArticleFragmentListener {
        fun showDetail(id: Int)
    }
}