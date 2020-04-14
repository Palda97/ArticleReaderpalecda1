package cz.cvut.palecda1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.cvut.palecda1.repository.ArticleRepository

class ArticleViewModelFactory(private val articleRepository: ArticleRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(articleRepository) as T
    }
}