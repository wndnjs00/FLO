package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // splash화면에서 원래의 테마로 돌려줌
        setTheme(R.style.Theme_FLO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // 하단 플레이어 클릭하면, SongActivity에 있는 제목,가수의 값이 같게 바뀌게
        // (하단플레이어 -> SongActivity로 데이터값 전달되게)

        // text값들을 string값으로 바꿔서 가져오기 (title,singer,second,playTime,isPlaying,music 에 해당하는 값들)
        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false,"music_lilac")


        //binding을 사용해서 id값 가져오기
        //mainPlayerCl눌렀을때 SongActivity로 이동
        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this, SongActivity::class.java))
            val intent = Intent(this, SongActivity::class.java)

            //putExtra를 사용해서 데이터값들을 보내줌
            intent.putExtra("title", song.title)    //putExtra를 사용해서 title(제목)데이터를 보내줌
            intent.putExtra("singer", song.singer)  //putExtra를 사용해서 singer(가수)데이터를 보내줌 (보낸데이터는 SongActivity에서 받음)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)

            startActivity(intent)
        }

        initBottomNavigation()


        //개발하면서 스스로 로그확인하기 위해
        Log.d("Song",song.title + song.singer)
    }



    // 하단 네비게이션바
    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}