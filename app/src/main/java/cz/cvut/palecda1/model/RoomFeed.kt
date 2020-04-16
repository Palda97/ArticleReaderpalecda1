package cz.cvut.palecda1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomFeed(@PrimaryKey(autoGenerate = false) val url: String,
                    val title: String,
                    val active: Boolean)