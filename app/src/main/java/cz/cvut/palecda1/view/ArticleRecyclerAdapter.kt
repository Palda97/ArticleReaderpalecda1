package cz.cvut.palecda1.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.cvut.palecda1.R
import cz.cvut.palecda1.article.Article
import cz.cvut.palecda1.databinding.TextFieldInLayoutBinding

class ArticleRecyclerAdapter(private val listener: (Article) -> Unit): RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder>() {
    var articleList: List<Article>? = null
    init {
        setHasStableIds(true)
    }
    fun updateArticleList(newArticleList: List<Article>){
        if (articleList == null) {
            articleList = newArticleList
            notifyItemRangeInserted(0, newArticleList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return articleList!!.size
                }

                override fun getNewListSize(): Int {
                    return newArticleList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return articleList!![oldItemPosition].url == newArticleList[newItemPosition].url
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newIssue = newArticleList[newItemPosition]
                    val oldIssue = articleList!![oldItemPosition]
                    return newIssue.url == oldIssue.url
                }
            })
            articleList = newArticleList
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
        return articleList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList!![position]

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