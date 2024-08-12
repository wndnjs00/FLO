package com.example.flo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.Album
import com.example.flo.Like
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.SongDao
import com.example.flo.SongDataBase
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.presentation.adapter.AlbumVPAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentAlbumBinding? = null
    private val information = arrayListOf("수록곡", "상세정보","영상")
    private val gson = Gson()
    private var isLiked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
        setViewPager()
        getAlbumData()
    }


    private fun clickListener() {
        with(binding){
            //albumBackIv버튼 클릭했을때
            albumBackIv.setOnClickListener{(context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()}

            //좋아요 눌렀을때
            albumLikeIv.setOnClickListener {favorateBtn(false)}

            //좋아요 해제했을때
            albumLikeClickIv.setOnClickListener {favorateBtn(true)}
        }
    }

    private fun getAlbumData(){
        // Home에서 넘어온 데이터 받아오기
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        // Home에서 넘어온 데이터를 반영
        isLiked = isLikedAlbum(album.id)
        setinit(album)

        setOnClickListeners(album)
    }

    private fun setinit(album: Album){
        with(binding){
            albumAlbumIv.setImageResource(album.coverImg!!)
            albumMusicTitleTv.text = album.title.toString()
            albumSingerNameTv.text = album.singer.toString()

            // 앨범 좋아요시 색상 바뀌게
            if(isLiked){
                albumLikeIv.setImageResource(R.drawable.ic_my_like_on)

            }else{
                albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
            }
        }
    }

    private fun setViewPager() {
        //view와 연결해주는 작업
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        //TabLayout과 ViewPager연결
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            //TabLayout에 들어갈 텍스트적음
                tab, position ->
            tab.text = information[position]
        }.attach()
    }


    //좋아요 눌렀을때 바뀌는 함수
    private fun favorateBtn(favorate : Boolean) {
        if (favorate){
            binding.albumLikeIv.visibility = View.VISIBLE
            binding.albumLikeClickIv.visibility = View.GONE
        }
        else{
            binding.albumLikeIv.visibility = View.GONE
            binding.albumLikeClickIv.visibility = View.VISIBLE
        }
    }

    // sharedpreference에 jwt값이 저장되어있는지 확인후, 좋아요버튼 변경하기
    private fun getJwt(): Int {
        val sharedpreference = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return sharedpreference?.getInt("jwt", 0) ?: 0
    }

    private fun likeAlbum(userId : Int, albumId : Int){
        val songDB = SongDataBase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    // 좋아요 눌렀는지 아닌지 확인해주는 함수
    private fun isLikedAlbum(albumId: Int) : Boolean {
        val songDB = SongDataBase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId = songDB.albumDao().isLikedAlbum(userId, albumId)

        return likeId != null
    }

    // 좋아요 해제했을때
    private fun disLikedAlbum(userId: Int, albumId: Int) {
//        val songDB = SongDataBase.getInstance(requireContext())!!
//        val userId = getJwt()
//
//        songDB.albumDao().disLikedAlbum(userId, albumId)
        val songDB = SongDataBase.getInstance(requireContext())!!
        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    private fun setOnClickListeners(album : Album){
        val userId : Int = getJwt()
        with(binding){
            if(isLiked){
                albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(userId, album.id)
            }else{
                albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }

        isLiked = !isLiked
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}