package cz.cvut.palecda1.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cz.cvut.palecda1.model.RoomFeed

@Dao
abstract class FeedDaoRoom: FeedDao {
    @Query("select * from roomfeed")
    abstract override fun feedList(): List<RoomFeed>

    @Insert
    abstract override fun insertFeed(roomFeed: RoomFeed)

    @Delete
    abstract override fun deleteFeed(roomFeed: RoomFeed)
}