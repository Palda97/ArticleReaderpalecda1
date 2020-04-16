package cz.cvut.palecda1.dao

import cz.cvut.palecda1.model.RoomFeed

class FeedDaoFake: FeedDao {

    val map: MutableMap<String, RoomFeed> = HashMap()

    init {
        for (i in 0 until 10){
            val url = "www.link$i.cz"
            val title = "Link $i"
            map[url] = RoomFeed(url, title, i % 2 == 0)
        }
    }

    override fun feedList(): List<RoomFeed> {
        return map.values.toList()
    }

    override fun insertFeed(roomFeed: RoomFeed) {
        map[roomFeed.url] = roomFeed
    }

    override fun deleteFeed(roomFeed: RoomFeed) {
        map.remove(roomFeed.url)
    }

    override fun deleteAll() {
        map.clear()
    }

    override fun activeFeedsOnly(): List<RoomFeed> {
        return feedList().filter { it.active }
    }
}