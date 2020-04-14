package cz.cvut.palecda1

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.cvut.palecda1.databinding.FragmentFeedBinding
import cz.cvut.palecda1.model.RoomFeed
import cz.cvut.palecda1.view.FeedRecyclerAdapter
import cz.cvut.palecda1.viewmodel.FeedViewModel
import java.util.Objects

class FeedFragment : Fragment() {

    //var listener: FeedFragmentListener? = null
    internal lateinit var binding: FragmentFeedBinding
    internal lateinit var viewModel: FeedViewModel
    internal lateinit var feedRecyclerAdapter: FeedRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_detail, container, false)

        //articleId = Objects.requireNonNull<Bundle>(arguments).getString(ID, "")

        //MVVM init + databinding

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)
        val view = binding.root

        feedRecyclerAdapter = FeedRecyclerAdapter { editFeed(it) }
        binding.insertFeedsHere.adapter = feedRecyclerAdapter

        viewModel = ViewModelProviders.of(this, MyInjector.FEED_VIEW_MODEL_FACTORY).get(FeedViewModel::class.java)
        viewModel.loadFeeds()

        viewModel.feedsLiveData.observe(this, Observer {
            if (it != null && it.isOk){
                feedRecyclerAdapter.updateFeedList(it.articles!!)
            }
            binding.executePendingBindings()
        })

        binding.viewmodel = viewModel


        return view
    }

    fun editFeed(roomFeed: RoomFeed){
        TODO("editFeed has not been implemented yet")
    }
    fun addFeed(){
        viewModel.addFeed(RoomFeed("testurl", "yikes"))
        viewModel.loadFeeds()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_feed, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addFeedItem -> {
                addFeed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        fun newInstance(): FeedFragment {
            val fragment = FeedFragment()
            //val args = Bundle()
            //args.putString(ID, url)
            //fragment.arguments = args
            return fragment
        }
    }

}