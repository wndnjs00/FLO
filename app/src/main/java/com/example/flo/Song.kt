package com.example.flo


//데이터 클래스
data class Song(
    val title : String = "",         //노래제목
    val singer : String = "",        //가수
    var second : Int = 0,            //현재 재생시간
    var playTime : Int = 60,          //총 재생시간
    var isPlaying : Boolean = false     //노래가 현재 재생되고 있는지

)
