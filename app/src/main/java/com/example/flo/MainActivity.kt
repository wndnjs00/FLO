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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 하단 플레이어 클릭하면, SongActivity에 있는 제목,가수의 값이 같게 바뀌게
        // (하단플레이어 -> SongActivity로 데이터값 전달되게)

        // text값들을 string값으로 바꿔서 가져오기
        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())

        //binding을 사용해서 id값 가져오기
        //mainPlayerCl눌렀을때 SongActivity로 이동
        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this, SongActivity::class.java))
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)    //putExtra를 사용해서 title(제목)데이터를 보내줌
            intent.putExtra("singer", song.singer)  //putExtra를 사용해서 singer(가수)데이터를 보내줌 (보낸데이터는 SongActivity에서 받음)
            startActivity(intent)
        }

        initBottomNavigation()


        //개발하면서 스스로 로그확인하기 위해
        Log.d("Song",song.title + song.singer)
    }

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