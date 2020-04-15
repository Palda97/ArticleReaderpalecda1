package cz.cvut.palecda1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.FragmentFeedBinding
import cz.cvut.palecda1.model.RoomFeed
import cz.cvut.palecda1.view.FeedDialogFragment
import cz.cvut.palecda1.view.FeedRecyclerAdapter
import cz.cvut.palecda1.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    //var listener: FeedFragmentListener? = null
    internal lateinit var binding: FragmentFeedBinding
    internal lateinit var viewModel: FeedViewModel
    internal lateinit var feedRecyclerAdapter: FeedRecyclerAdapter

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_detail, container, false)

        //articleId = Objects.requireNonNull<Bundle>(arguments).getString(ID, "")

        //MVVM init + databinding

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_feed, container, false)
        val view = binding.root

        feedRecyclerAdapter = FeedRecyclerAdapter ({editFeed(it) }, {deleteFeed(it)})
        binding.insertFeedsHere.adapter = feedRecyclerAdapter

        //viewModel = ViewModelProviders.of(this, MyInjector.FEED_VIEW_MODEL_FACTORY).get(FeedViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.loadFeeds()

        viewModel.feedsLiveData.observe(this, Observer {
            if (it != null && it.isOk){
                feedRecyclerAdapter.updateFeedList(it.articles!!)
                Log.d(TAG, "it.isOk = true")
            }
            binding.mail = it
            binding.executePendingBindings()
        })

        binding.viewmodel = viewModel


        return view
    }

    fun editFeed(roomFeed: RoomFeed){
        val newFragment = FeedDialogFragment.editFeed(viewModel, roomFeed)
        newFragment.show(fragmentManager!!, "editFeedDialog")
    }

    fun deleteFeed(roomFeed: RoomFeed): Boolean {
        val dialogBuilder = AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.delete_feed) + "(${roomFeed.title})")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                Log.d(
                    TAG, getString(
                        R.string.delete
                    ))
                viewModel.deleteFeed(roomFeed)
                viewModel.loadFeeds()
            }
            .setNeutralButton(getString(R.string.cancel)) { _, _ ->
                Log.d(
                    TAG, getString(
                        R.string.cancel
                    ))
            }
        //val alertDialog = dialogBuilder.show()
        dialogBuilder.show()
        return true
    }

    fun addFeed(){
        val newFragment = FeedDialogFragment(viewModel)
        newFragment.show(fragmentManager!!, "newFeedDialog")
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
            R.id.resetFeedsItem -> {
                viewModel.deleteAll()
                viewModel.addFeed(RoomFeed(getString(R.string.default_feed_url), getString(
                    R.string.default_feed_title
                )))
                viewModel.loadFeeds()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        const val TAG = "FeedFragment"

        fun newInstance(): FeedFragment {
            val fragment = FeedFragment()
            //val args = Bundle()
            //args.putString(ID, url)
            //fragment.arguments = args
            return fragment
        }
    }

}