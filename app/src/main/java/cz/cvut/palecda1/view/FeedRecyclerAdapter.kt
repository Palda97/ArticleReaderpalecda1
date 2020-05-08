package cz.cvut.palecda1.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.FeedFieldInLayoutBinding
import cz.cvut.palecda1.model.RoomFeed

class FeedRecyclerAdapter(private val editListener: (RoomFeed) -> Unit, private val deleteListener: (RoomFeed) -> Boolean, private val onChange: (RoomFeed, Boolean) -> Unit): RecyclerView.Adapter<FeedRecyclerAdapter.FeedViewHolder>() {
    var feedList: List<RoomFeed>? = null
    init {
        //setHasStableIds(true)
    }
    fun updateFeedList(newFeedList: List<RoomFeed>){
        if (feedList == null) {
            feedList = newFeedList
            notifyItemRangeInserted(0, newFeedList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return feedList!!.size
                }

                override fun getNewListSize(): Int {
                    return newFeedList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return feedList!![oldItemPosition].url == newFeedList[newItemPosition].url
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newIssue = newFeedList[newItemPosition]
                    val oldIssue = feedList!![oldItemPosition]
                    return newIssue.url == oldIssue.url
                }
            })
            feedList = newFeedList
            result.dispatchUpdatesTo(this)
        }
    }

    class FeedViewHolder(val binding: FeedFieldInLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = DataBindingUtil
            .inflate<FeedFieldInLayoutBinding>(
                LayoutInflater.from(parent.context), R.layout.feed_field_in_layout,
                parent, false)
        return FeedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return feedList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feedList!![position]

        holder.binding.feed = feed
        holder.binding.feedActiveToggle.setOnCheckedChangeListener{_, it ->
            onChange(feed, it)
            holder.binding.feed = feed
            holder.binding.executePendingBindings()
        }
        holder.binding.executePendingBindings()

        holder.itemView.setOnClickListener {
            editListener(feed)
        }

        holder.itemView.setOnLongClickListener {
            deleteListener(feed)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}