package cz.cvut.palecda1.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.FragmentArticleBinding
import cz.cvut.palecda1.model.RoomArticle
import cz.cvut.palecda1.repository.MailPackage
import cz.cvut.palecda1.view.ArticleRecyclerAdapter
import cz.cvut.palecda1.viewmodel.ArticleViewModel

class ArticleFragment : Fragment() {

    var listener: ArticleFragmentListener? = null
    internal lateinit var binding: FragmentArticleBinding
    internal lateinit var viewModel: ArticleViewModel
    internal lateinit var articleRecyclerAdapter: ArticleRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_article, container, false)
        val view = binding.root

        articleRecyclerAdapter = ArticleRecyclerAdapter { listener!!.showDetail(it) }
        binding.insertArticlesHere.adapter = articleRecyclerAdapter

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.loadArticles()

        viewModel.articlesLiveData.observe(this, Observer {
            var empty = false
            if (it != null && it.isOk){
                articleRecyclerAdapter.updateArticleList(it.articles!!)
                Log.d(TAG, "it.isOk = true")
                if(it.articles.isEmpty())
                    empty = true
            }
            binding.mail = it
            if(empty)
                binding.mail = MailPackage(null, MailPackage.ERROR, getString(R.string.refresh_or_add_feeds))
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_article, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.configFeedItem -> {
                listener!!.configFeeds()
                true
            }
            R.id.refreshItem -> {
                viewModel.refreshArticles()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    interface ArticleFragmentListener {
        fun showDetail(roomArticle: RoomArticle)
        fun configFeeds()
    }

    companion object{
        const val TAG = "ArticleFragment"
    }
}