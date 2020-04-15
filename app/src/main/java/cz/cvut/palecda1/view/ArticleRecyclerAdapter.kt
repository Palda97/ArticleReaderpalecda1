package cz.cvut.palecda1.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.TextFieldInLayoutBinding
import cz.cvut.palecda1.model.RoomArticle

class ArticleRecyclerAdapter(private val listener: (RoomArticle) -> Unit): RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder>() {
    var roomArticleList: List<RoomArticle>? = null
    init {
        setHasStableIds(true)
    }
    fun updateArticleList(newRoomArticleList: List<RoomArticle>){
        if (roomArticleList == null) {
            roomArticleList = newRoomArticleList
            notifyItemRangeInserted(0, newRoomArticleList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return roomArticleList!!.size
                }

                override fun getNewListSize(): Int {
                    return newRoomArticleList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return roomArticleList!![oldItemPosition].url == newRoomArticleList[newItemPosition].url
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newIssue = newRoomArticleList[newItemPosition]
                    val oldIssue = roomArticleList!![oldItemPosition]
                    return newIssue.url == oldIssue.url
                }
            })
            roomArticleList = newRoomArticleList
            result.dispatchUpdatesTo(this)
        }
    }

    class ArticleViewHolder(val binding: TextFieldInLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = DataBindingUtil
            .inflate<TextFieldInLayoutBinding>(
                LayoutInflater.from(parent.context), R.layout.text_field_in_layout,
                parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return roomArticleList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = roomArticleList!![position]

        holder.binding.article = article
        holder.binding.executePendingBindings()

        holder.itemView.setOnClickListener {
            listener(article)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}