package cz.cvut.palecda1

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var articleTextView: TextView
    //var listener: DetailFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        articleTextView = view.findViewById(R.id.textViewForArticle) as TextView

        val id = Objects.requireNonNull<Bundle>(arguments).getInt(ID)
        showArticle(id)

        return view
    }

    fun showArticle(id: Int){
        articleTextView.text = Html.fromHtml(DataStorage.articleArray[id])
    }

    /*override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is DetailFragmentListener){
            listener = context
        }else{
            throw UnsupportedOperationException("Context is not DetailFragmentListener!")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface DetailFragmentListener {
        //fun showDetail(id: Int)
    }*/

    companion object {

        private const val ID = "ID"

        fun newInstance(number: Int): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putInt(ID, number)
            fragment.arguments = args
            return fragment
        }
    }

}