package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener{
            finish()
        }


        //songMiniplayerIv버튼 눌렀을때 재생버튼으로 이미지 바뀌게
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }

        //songPauseIv버튼 눌렀을때 정지버튼으로 이미지 바뀌게
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }


        //songLikeIv버튼 눌렀을때 좋아요 눌리게
        binding.songLikeIv.setOnClickListener {
            favorateBtn(false)
        }

        //좋아요 해제되게
        binding.songLikeClickIv.setOnClickListener {
            favorateBtn(true)
        }

        //금지버튼 눌렀을때 눌리게
        binding.songUnlikeIv.setOnClickListener {
            noAction(false)
        }

        //금지버튼 해제되게
        binding.songUnlikeClickIv.setOnClickListener {
            noAction(true)
        }


        //MainActivity에서 보낸데이터 받음(제목,가수)
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            //MainActivity에서 보낸데이터 받아서, 제목과 가수의 이름을 바꿔줌
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
    }



    //재생버튼 눌렀을때 이미지 바뀌는 함수
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }


    //좋아요 눌렀을때 바뀌는 함수
    fun favorateBtn(isPlaying: Boolean){
        if (isPlaying){
            binding.songLikeIv.visibility = View.VISIBLE
            binding.songLikeClickIv.visibility = View.GONE
        }
        else{
            binding.songLikeIv.visibility = View.GONE
            binding.songLikeClickIv.visibility = View.VISIBLE
        }
    }

    //금지버튼 눌렀을때 바뀌는 함수
    fun noAction(go: Boolean){
        if (go){
            binding.songUnlikeIv.visibility = View.VISIBLE
            binding.songUnlikeClickIv.visibility = View.GONE
        }
        else{
            binding.songUnlikeIv.visibility = View.GONE
            binding.songUnlikeClickIv.visibility = View.VISIBLE
        }
    }



}