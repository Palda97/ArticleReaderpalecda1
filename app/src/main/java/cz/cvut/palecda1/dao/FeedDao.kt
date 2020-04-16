package cz.cvut.palecda1.dao

import androidx.lifecycle.LiveData
import cz.cvut.palecda1.model.RoomFeed

interface FeedDao {
    fun feedList(): List<RoomFeed>
    fun insertFeed(roomFeed: RoomFeed)
    fun deleteFeed(roomFeed: RoomFeed)
    fun deleteAll()
    fun activeFeedsOnly(): List<RoomFeed>
}