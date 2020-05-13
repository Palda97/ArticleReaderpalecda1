package cz.cvut.palecda1.dao

import androidx.room.*
import cz.cvut.palecda1.model.RoomFeed

@Dao
abstract class FeedDaoRoom: FeedDao {
    @Query("select * from roomfeed order by url asc")
    abstract override fun feedList(): List<RoomFeed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertFeed(roomFeed: RoomFeed)

    @Delete
    abstract override fun deleteFeed(roomFeed: RoomFeed)

    @Query("delete from roomfeed")
    abstract override fun deleteAll()

    @Query("select * from roomfeed where active = 1 order by url asc")
    abstract override fun activeFeedsOnly(): List<RoomFeed>
}