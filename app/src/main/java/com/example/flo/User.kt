package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    var email : String,
    var password : String
){
    // 사용자가 추가될때마다 id를 사용해서 추가될수있도록
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
