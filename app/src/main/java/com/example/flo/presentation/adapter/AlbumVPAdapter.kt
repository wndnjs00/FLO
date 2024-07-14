package com.example.flo.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.presentation.fragment.DetailFragment
import com.example.flo.presentation.fragment.SongFragment
import com.example.flo.presentation.fragment.VideoFragment

class AlbumVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){


    //3개의 fragment만들거임 (개수 3개)
    override fun getItemCount(): Int = 3


    // 수록곡,상세정보,영상의 각각 다른 fragment를 만들어야함
    // fragment를 각각 따로 만든다음 연결해줌
    override fun createFragment(position: Int): Fragment {
        //position에 따라(수록곡,상세정보,영상을 눌렀을때에 따라) 다른 fragment 보여줌
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()

        }
    }

}