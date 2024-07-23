package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao{

    @Insert
    fun insert(user : User)

    // 모든 User데이터 가져옴
    @Query("SELECT * FROM UserTable")
    fun getUsers() : List<User>

    // 한명의 User에 대한 정보 가져옴(email과 password가 똑같은 User의 모든정보 가져오라는뜻)
    // 사용자가 입력한 email,password와 일치하는것이 있는지 확인해주는 함수
    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email : String, password : String) : User?
}



