package com.example.flo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.Album
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.SongDataBase
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.presentation.adapter.AlbumRVAdapter
import com.example.flo.presentation.adapter.BannerVPAdapter
import com.example.flo.presentation.adapter.BigBannerVPAdapter
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentHomeBinding? = null

    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB : SongDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()
        setViewPager2()
        addDataList()
        setRecyclerView()
    }

    private fun setViewPager(){
        //맨위에있는 viewpager만들기
        //list안에 fragment추가
        val bigbannerAdpter = BigBannerVPAdapter(this)
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_default_4_x_1))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp2))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp3))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp4))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp5))

        //viewpager와 adapter연결
        with(binding.homePannelBackgroundVp){
            adapter = bigbannerAdpter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        //viewpager와 indicator연결
        with(binding.homePannelIndicator){
            setViewPager(binding.homePannelBackgroundVp)
            setViewPager(binding.homeBannerVp)
        }
    }


    private fun setViewPager2(){
        //viewpager를 만들기위해 fragment데이터를 추가하고, viewpager연결해줌

        //list안에 fragment추가
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        //viewpager와 adapter연결
        with(binding.homeBannerVp){
            adapter = bannerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun addDataList(){
        // 데이터리스트 생성 더미데이터 (실제앱에서는 서버에서 받아온 데이터사용)
        // 앨범 데이터에 데이터를 추가
        songDB = SongDataBase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())
    }


    private fun setRecyclerView() {
        val albumRVAdapter = AlbumRVAdapter { album ->
            // AlbumFragment로 이동
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment().apply {
                    // bundle로 AlbumFragment에 데이터 넘겨주기
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val albumJson = gson.toJson(album)
                        putString("album", albumJson)
                    }
                }).commitAllowingStateLoss()
        }

        with(binding.homeTodayMusicAlbumRv) {
            adapter = albumRVAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        // 어댑터 갱신
        albumRVAdapter.submitList(albumDatas)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}