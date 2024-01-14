package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){


    lateinit var binding : ActivitySongBinding

    //Song 데이터클래스를 초기화하기위해 전역변수 선언
    lateinit var song: Song

    lateinit var timer : Timer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initSong,setPlayer 함수 데이터 받아오기
        initSong()
        setPlayer(song)


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



//        //MainActivity에서 보낸데이터 받음(제목,가수)
//        if (intent.hasExtra("title") && intent.hasExtra("singer")){
//            //MainActivity에서 보낸데이터 받아서, 제목과 가수의 이름을 바꿔줌
//            binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
//            binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
//        }

    }


    //activity파괴될때 호출되는 onDestroy함수
    override fun onDestroy() {
        super.onDestroy()
        //thread 종료
        timer.interrupt()
    }


    //Song 데이터클래스를 초기화하기위한 함수
    private fun initSong(){
        //MainActivity에서 보낸데이터 받음
        //만약 title값과 singer값이 넘어왔으면, getExtra를 사용해서 받아옴
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        //SongActivity가 시작되는 시점에서 timer를 초기화해주기 위해 startTimer()를 호출
        startTimer()
    }


    // SongActivity화면에 받아와서 초기화된 Song에 대한 정보를 렌더링해주는 함수
    private fun setPlayer(song : Song){
        //MainActivity에서 보낸데이터 받아서, 제목과 가수의 이름을 바꿔줌
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        //현재 재생시간,총 재생시간,프로그레스바에 대한
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)

    }




    //재생버튼 눌렀을때 이미지 바뀌는 함수
    fun setPlayerStatus(isPlaying : Boolean){
        //재생버튼을 눌렀을때 song.isPlaying값과 timer.isPlaying을 초기화
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

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


    private fun startTimer(){
        // song의 총재생시간, 재생중 의 여부를 생성자의 입력으로 받음
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }



    //Thread를 사용해서 노래가 재생되면 seakbar와 텍스트의 값이 바뀌도록
    //시간이 지남에따라 seakbar와 타이머 텍스트의 값을 바꿔줘야하기 때문에 inner class로 생성
    //보여주고자 하는 노래가 총 몇초인지, 지금 노래가 재생중인지
    inner class Timer(private val playTime : Int, var isPlaying: Boolean = true) : Thread(){
        //노래의 진행시간을 second와 mills로 나눠서 받아옴
        private var second : Int = 0
        private var mills : Float = 0F


        //해당 스레드가 실행할 코드작성 (run함수내에 작성된코드는 순차적으로 실행)
        override fun run() {
            super.run()

            // try-catch문에서 interrupt를 사용해서 thread를 멈추고, 오류가 발생했을때 특정명령을 실행하도록함
            try {
                //Timer는 계속 재생되어야하므로 when(true)를 사용해서 반복시켜줌 (무한루프)
                //second >= playTime일때 탈출하도록 설정하여, 노래의 총재생시간동안만 스레드가 실행되도록
                while(true) {
                    if(second >= playTime) {
                        break
                    }


                    // 0.2초 대기
                    while (!isPlaying) {
                        sleep(200)
                    }

                    // isPlaying이 true일때만,50ms마다 프로그레스바를 슬라이드함 (즉, 초당 20번의 slide를 진행해서 자연스러운 슬라이딩효과줌)
                    if(isPlaying) {
                        sleep(50)
                        mills += 50

                        //progress바
                        //mills는 ms단위이고, playTime은 초단위 -> playTime에 1000을 곱해야함(근데 여기서는 백분율이아닌 십만분율을 사용하고있기때문에 100곱함)
                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                        //진행시간 타이머 (mills = 1000 = 1초)
                        //1초가 지날때마다 second(현재재생시간)을 1씩 증가시키고, songStartTimeTv의 텍스트 갱신
                        if (mills % 1000 == 0F){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second ++
                        }

                    }
                }

            }catch (e: InterruptedException){
                Log.d("SongActivity", "쓰레드가 죽었습니다 ${e.message}")
            }

        }
    }


}