package cz.cvut.palecda1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomArticle(@PrimaryKey(autoGenerate = false) val url: String,
                       val body: String)