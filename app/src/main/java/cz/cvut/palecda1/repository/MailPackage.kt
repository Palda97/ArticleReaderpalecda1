package cz.cvut.palecda1.repository

class MailPackage<content>(val articles: content?, private val status: Int, val msg: String) {
    val isLoading: Boolean
        get() = status == LOADING
    val isOk: Boolean
        get() = status == OK && articles != null
    val isError: Boolean
        get() = status == ERROR

    companion object{
        const val LOADING = 0
        const val OK = 1
        const val ERROR = 2
    }
}