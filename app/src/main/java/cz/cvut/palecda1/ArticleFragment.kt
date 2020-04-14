package cz.cvut.palecda1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.cvut.palecda1.article.Article
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.cvut.palecda1.databinding.FragmentArticleBinding
import cz.cvut.palecda1.view.ArticleRecyclerAdapter
import cz.cvut.palecda1.viewmodel.ArticleViewModel

class ArticleFragment : Fragment() {

    var listener: ArticleFragmentListener? = null
    internal lateinit var binding: FragmentArticleBinding
    internal lateinit var viewModel: ArticleViewModel
    internal lateinit var articleRecyclerAdapter: ArticleRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)
        val view = binding.root

        articleRecyclerAdapter = ArticleRecyclerAdapter { listener!!.showDetail(it) }
        binding.insertArticlesHere.adapter = articleRecyclerAdapter

        viewModel = ViewModelProviders.of(this, MyInjector.ARTICLE_VIEW_MODEL_FACTORY).get(ArticleViewModel::class.java)
        viewModel.loadArticles()

        viewModel.articlesLiveData.observe(this, Observer {
            if (it != null && it.isOk){
                articleRecyclerAdapter.updateArticleList(it.articles!!)
            }
            binding.executePendingBindings()
        })

        binding.viewmodel = viewModel


        return view
    }

    override fun onAttach(context: Context) {
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

    /*private fun showArticles(inflater: LayoutInflater, container: ViewGroup?, articles: Array<Article>){
        articles.forEachIndexed { index, s ->
            val lView = inflater.inflate(R.layout.text_field_in_layout, container)
            //lView.textViewInLayout.text = Html.fromHtml(s.body)
            lView.textViewInLayout.text = HtmlCompat.fromHtml(s.body, HtmlCompat.FROM_HTML_MODE_LEGACY)
            lView.textViewInLayout.setOnClickListener{listener?.showDetail(index)}
            pLayout.addView(lView, pLayout.childCount)
        }
    }*/

    interface ArticleFragmentListener {
        fun showDetail(article: Article)
    }
}