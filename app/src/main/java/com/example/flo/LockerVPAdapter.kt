package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    //2개의 fragment만들거임 (개수 2개)
    override fun getItemCount(): Int = 2


    // 저장한곡, 음악파일 각각 다른 fragment를 만들어야함
    // fragment를 각각 따로 만든다음 연결해줌
    override fun createFragment(position: Int): Fragment {
        //position에 따라(저장한 곡, 음악파일을 눌렀을때에 따라) 다른 fragment보여줌
        return when(position){
            0 -> SaveSongFragment()
            else -> SongFileFragment()
    }
}


}