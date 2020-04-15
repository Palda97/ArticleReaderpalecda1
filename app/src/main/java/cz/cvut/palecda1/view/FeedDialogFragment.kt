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
//lateinit var viewModel: FeedViewModel

class FeedDialogFragment(val viewModel: FeedViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DataBindingUtil.inflate(activity!!.layoutInflater, R.layout.dialog_new_feed, null, false)
        val view = binding.root

        //viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        val builder = AlertDialog.Builder(requireActivity())
        //builder.setMessage("Message")
        builder.setView(view)
            .setTitle("Add Feed")
            .setPositiveButton("ADD") { dialog, which ->
                Log.d(TAG, "ADD")
                val title: String = binding.editTitle.text.toString()
                val url: String = binding.editUrl.text.toString()
                viewModel.addFeed(RoomFeed(url, title))
                viewModel.loadFeeds()
            }
            .setNeutralButton("CANCEL") { dialog, which ->
                Log.d(TAG, "CANCEL")
            }
        return builder.create()
    }

    companion object{
        const val TAG = "FeedDialogFragment"
    }
}