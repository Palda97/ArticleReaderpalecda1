package cz.cvut.palecda1.view.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.FragmentDetailBinding
import cz.cvut.palecda1.view.HtmlFactory
import cz.cvut.palecda1.viewmodel.ArticleViewModel
import java.util.Objects

class DetailFragment : Fragment() {

    private lateinit var articleTextView: TextView
    private lateinit var articleId: String

    internal lateinit var binding: FragmentDetailBinding
    internal lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_detail, container, false)

        articleId = Objects.requireNonNull<Bundle>(arguments).getString(ID, "")

        //MVVM init + databinding

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail, container, false)
        val view = binding.root

        binding.detailTextView.movementMethod = LinkMovementMethod.getInstance()

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.loadArticleById(articleId)

        viewModel.roomArticleLiveData.observe(this, Observer {
            if (it != null && it.isOk){
                binding.article = HtmlFactory.toHtml(it.articles!!)
            }
            binding.mail = it
            binding.executePendingBindings()
        })

        //binding.viewmodel = viewModel
        //MVVM END
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.shareItem -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type =
                    TEXT_PLAIN
                val body = articleId
                //val subject = ""
                sharingIntent.putExtra(Intent.EXTRA_TEXT, body)
                //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
                true
            }
            R.id.openLinkItem -> {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articleId)))
                }catch (e: ActivityNotFoundException){
                    Toast.makeText(context!!, "<$articleId> " + getString(R.string.is_not_a_valid_url), Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val ID = "ID"
        private const val TEXT_PLAIN = "text/plain"

        fun newInstance(url: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(ID, url)
            fragment.arguments = args
            return fragment
        }
    }

}