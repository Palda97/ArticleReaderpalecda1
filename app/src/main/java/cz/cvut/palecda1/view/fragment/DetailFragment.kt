package cz.cvut.palecda1.view.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.*
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.cvut.palecda1.Injector
import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.FragmentDetailBinding
import cz.cvut.palecda1.view.HtmlFactory
import cz.cvut.palecda1.viewmodel.ArticleViewModel
import kotlinx.coroutines.*
import kotlin.math.log
import kotlin.math.roundToInt

class DetailFragment : Fragment() {

    private lateinit var articleTextView: TextView
    private var articleId: String? = null

    internal lateinit var binding: FragmentDetailBinding
    internal lateinit var viewModel: ArticleViewModel
    private var shouldScroll = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_detail, container, false)

        articleId = arguments?.getString(ID, null)

        //MVVM init + databinding

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail, container, false
        )
        val view = binding.root

        binding.detailTextView.movementMethod = LinkMovementMethod.getInstance()

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        articleId?.let { viewModel.loadArticleById(it) }

        shouldScroll = false

        viewModel.roomArticleLiveData.observe(this, Observer {
            if (it != null && it.isOk) {
                binding.article = HtmlFactory.toHtml(it.mailContent!!.title, it.mailContent.body)
                if(shouldScroll)
                    scrollAfter(200)
                shouldScroll = true
            }
            binding.mail = it
            binding.executePendingBindings()
        })

        //binding.viewmodel = viewModel
        //MVVM END
        return view
    }

    private suspend fun scrollOnMain() {
        withContext(Dispatchers.Main) {
            val totalHeight = binding.scrollViewInDetail.getChildAt(0).height
            //binding.scrollViewInDetail.fling(-10000)
            //binding.scrollViewInDetail.fullScroll(ScrollView.FOCUS_UP)
            //binding.scrollViewInDetail.smoothScrollTo(0, 0)
            binding.scrollViewInDetail.fling(totalHeight * -3 )
            Log.d(TAG, "scrollMain totalHeight: $totalHeight")
        }
    }

    private fun scrollAfter(ms: Int) {
        suspend fun scrolling(waitTime: Int) {
            delay(waitTime.toLong())
            scrollOnMain()
        }
        CoroutineScope(Dispatchers.IO).launch { scrolling(ms) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun shareCurrentArticle() {
        if (articleId == null) {
            context?.let { Injector.noArticleSelectedToast(it) }
            return
        }
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = TEXT_PLAIN
        val body = articleId
        //val subject = ""
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body)
        //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
    }

    private fun openLink() {
        val articleUrl = viewModel.roomArticleLiveData.value?.mailContent?.url
        if (articleUrl == null) {
            context?.let { Injector.noArticleSelectedToast(it) }
            return
        }
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context!!,
                "<$articleId> " + getString(R.string.is_not_a_valid_url),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.shareItem -> {
                shareCurrentArticle()
                true
            }
            R.id.openLinkItem -> {
                openLink()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val ID = "ID"
        private const val TEXT_PLAIN = "text/plain"
        private const val TAG = "DetailFragment"

        fun newInstance(url: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(ID, url)
            fragment.arguments = args
            return fragment
        }
    }

}