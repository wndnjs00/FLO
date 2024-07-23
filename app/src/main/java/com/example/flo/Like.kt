package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikeTable")
data class Like(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    var userId : Int,
    var albumId : Int
)
