package cz.cvut.palecda1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.cvut.palecda1.repository.FeedRepository

class FeedViewModelFactory(private val feedRepository: FeedRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedViewModel(feedRepository) as T
    }
}