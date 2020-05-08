package cz.cvut.palecda1.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.DialogNewFeedBinding
import cz.cvut.palecda1.model.RoomFeed
import cz.cvut.palecda1.viewmodel.FeedViewModel

lateinit var binding: DialogNewFeedBinding

//class FeedDialogFragment(val viewModel: FeedViewModel): DialogFragment() {
class FeedDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        binding = DataBindingUtil.inflate(activity!!.layoutInflater, R.layout.dialog_new_feed, null, false)
        val view = binding.root

        var editMode = false
        var oldTitle = ""
        var oldUrl = ""
        var oldActive = false

        if(arguments != null){
            editMode = arguments!!.getBoolean(FEED, false)
            if(editMode){
                oldTitle = arguments!!.getString(TITLE, "")
                oldUrl = arguments!!.getString(URL, "")
                oldActive = arguments!!.getBoolean(ACTIVE, true)
                binding.editTitle.setText(oldTitle)
                binding.editUrl.setText(oldUrl)
                binding.switchActive.isChecked = oldActive
            }
        }

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
            .setTitle(getString(R.string.add_feed))
            .setPositiveButton(getString(R.string.add)) { _, _ ->
                if(editMode){
                    viewModel.deleteFeed(RoomFeed(oldUrl, oldTitle, oldActive))
                    viewModel.loadFeeds()
                }
                val title: String = binding.editTitle.text.toString()
                val url: String = binding.editUrl.text.toString()
                val active: Boolean = binding.switchActive.isChecked
                viewModel.addFeed(RoomFeed(url, title, active))
                viewModel.loadFeeds()
            }
            .setNeutralButton(getString(R.string.cancel)) { _, _ ->
                Log.d(TAG, getString(R.string.cancel))
            }
        if(editMode)
            builder.setTitle(getString(R.string.edit_feed))
        return builder.create()
    }

    companion object{
        const val TAG = "FeedDialogFragment"

        const val URL = "URL"
        const val TITLE = "TITLE"
        const val ACTIVE = "ACTIVE"
        const val FEED = "FEED"

        fun editFeed(roomFeed: RoomFeed): FeedDialogFragment {
            val fragment = FeedDialogFragment()
            val args = Bundle()
            args.putString(URL, roomFeed.url)
            args.putString(TITLE, roomFeed.title)
            args.putBoolean(ACTIVE, roomFeed.active)
            args.putBoolean(FEED, true)
            fragment.arguments = args
            return fragment
        }
    }
}