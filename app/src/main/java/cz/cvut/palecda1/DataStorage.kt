package cz.cvut.palecda1

object DataStorage {
    val articles: List<String>
        get() = articleArray.toList()
    val articleArray: Array<String> = Array(10) {
        "<h1>Article $it</h1>Article $it"
    }
}