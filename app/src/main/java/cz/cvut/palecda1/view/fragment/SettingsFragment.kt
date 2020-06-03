package cz.cvut.palecda1.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import cz.cvut.palecda1.R
import cz.cvut.palecda1.databinding.FragmentSettingsBinding
import cz.cvut.palecda1.viewmodel.ArticleViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_settings, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        viewModelBinding()
        componentsBinding()
        return binding.root
    }

    private fun viewModelBinding() {
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        /*viewModel.deleteOld.observe(this, Observer {
            if (it == null)
                return@Observer
            binding.deleteArticles = it
            Log.d(TAG, "delete articles = $it")
            binding.executePendingBindings()
        })*/
        viewModel.deleteOld.observe(this, Observer {
            if (it == null)
                return@Observer
            Log.d(TAG, "delete articles = $it")
        })
    }

    private fun componentsBinding() {
        binding.switchKeepOldArticles.isChecked = !viewModel.deleteOld.value!!
        binding.switchKeepOldArticles.setOnCheckedChangeListener { _, keep ->
            viewModel.setDeleteOld(!keep)
        }
    }

    companion object {
        const val TAG = "SettingsFragment"
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
