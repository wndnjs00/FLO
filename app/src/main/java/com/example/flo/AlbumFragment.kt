package com.example.flo

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentAlbumBinding? = null
    //fragment_album TabLayout에 들어갈 텍스트
    private val information = arrayListOf("수록곡", "상세정보","영상")
    private val gson = Gson()


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
    }



    private fun clickListener() {
        with(binding){
            val albumJson = arguments?.getString("album")
            val album = gson.fromJson(albumJson, Album::class.java)
            setinit(album)

            //albumBackIv버튼 클릭했을때
            albumBackIv.setOnClickListener{(context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()}

            //좋아요 눌렀을때
            albumLikeIv.setOnClickListener {favorateBtn(false)}

            //좋아요 해제했을때
            albumLikeClickIv.setOnClickListener {favorateBtn(true)}
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

    private fun setinit(album: Album){
        with(binding){
            albumAlbumIv.setImageResource(album.coverImg!!)
            albumMusicTitleTv.text = album.title.toString()
            albumSingerNameTv.text = album.singer.toString()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}