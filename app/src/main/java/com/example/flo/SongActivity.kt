package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity(){


    lateinit var binding : ActivitySongBinding
    lateinit var timer : Timer
    // 음악을 재생시켜줄 mediaPlayer 객체 추가
    private var mediaPlayer : MediaPlayer? = null
    // Gson 선언
    private var gson : Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB : SongDataBase
    var nowPos = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListner()
    }


    //activity파괴될때 호출되는 onDestroy함수(불필요한 리소스방지)
    override fun onDestroy() {
        super.onDestroy()
        //thread 종료
        timer.interrupt()
        mediaPlayer?.release()  //미디어플레이어가 갖고있던 리소스 해제
        mediaPlayer = null     //미디어플레이어 해제
    }


    private fun initPlayList(){
        songDB = SongDataBase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }


    private fun initClickListner(){
        binding.songDownIb.setOnClickListener{
            finish()
        }

        //songMiniplayerIv버튼 눌렀을때 재생버튼으로 이미지 바뀌게
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        //songPauseIv버튼 눌렀을때 정지버튼으로 이미지 바뀌게
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }

        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

//        //songLikeIv버튼 눌렀을때 좋아요 눌리게
//        binding.songLikeIv.setOnClickListener {
//            favorateBtn(false)
//        }
//
//        //좋아요 해제되게
//        binding.songLikeClickIv.setOnClickListener {
//            favorateBtn(true)
//        }
//
//        //금지버튼 눌렀을때 눌리게
//        binding.songUnlikeIv.setOnClickListener {
//            noAction(false)
//        }
//
//        //금지버튼 해제되게
//        binding.songUnlikeClickIv.setOnClickListener {
//            noAction(true)
//        }
    }


    // sharedPreferences에서 id값을 받아온후, songId를 통해서 songs와 비교해서 index값을 구하도록하는 함수
    private fun initSong(){
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songId = sharedPreferences.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        Log.d("now Song ID", songs[nowPos].id.toString())
        //SongActivity가 시작되는 시점에서 timer를 초기화해주기 위해 startTimer()를 호출
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId : Int) : Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return 1
            }
        }
        return 0
    }


    // 노래 이전,이후 재생
    private fun moveSong(direct : Int){
        if(nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct
        timer.interrupt()
        startTimer()

        //리소스 해제
        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }


    // SongActivity화면에 받아와서 초기화된 Song에 대한 정보를 렌더링해주는 함수
    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        //현재 재생시간,총 재생시간,프로그레스바에 대한
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        // String값인 muscic 리소스를 반환
        val music = resources.getIdentifier(song.music,"raw", this.packageName)
        if(music != 0){
            // 리소스를 MediaPlayer한테 올림 (MediaPlayer한테 이음악 재생한다고 알림)
            mediaPlayer = MediaPlayer.create(this,music)
            setPlayerStatus(song.isPlaying)
        }else{
            Log.d("SongActivity", "Music resource not found: ${song.music}")
        }
    }


    //재생버튼 눌렀을때 이미지 바뀌는 함수
    private fun setPlayerStatus(isPlaying : Boolean){
        //재생버튼을 눌렀을때 song.isPlaying값과 timer.isPlaying을 초기화
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            //음악재생
            mediaPlayer?.start()
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            //음악중지
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
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
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
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

                    // isPlaying이 true일때만(노래가 현재재생중일때만),50ms마다 프로그레스바를 슬라이드함 (즉, 초당 20번의 slide를 진행해서 자연스러운 슬라이딩효과줌)
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


    // 사용자가 포커스 잃었을때 음악이 중지
    override fun onPause() {
        super.onPause()

        // 음악이 몇초까지 재생되었는지 Song데이터 클래스에 반영
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)

        editor.apply()
    }



}