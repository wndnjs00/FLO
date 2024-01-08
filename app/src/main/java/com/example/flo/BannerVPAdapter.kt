package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


//viewpager를 만들기위한 adapter
class BannerVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {


    //리스트를 만들어서 여러개의 fragment들을 담아둠
    private val fragmentlist : ArrayList<Fragment> = ArrayList()


    //데이터 몇개 전달할건지
    //fragmentlist에 담긴 개수를 전달하겠다
    override fun getItemCount(): Int {
        return fragmentlist.size
    }

    //framgmentlist안에 있는 item들을 생성해주는 함수
    override fun createFragment(position: Int): Fragment = fragmentlist[position]


    //처음에 Fragment에는 아무것도 없기때문에 homefragment에 추가해줄 fragment작성
    //각각의 똑같은 이미지만 바꿔서 fragment만들어줌(하나의 fragment로 가능)
    fun addFragment(fragment: Fragment){
        fragmentlist.add(fragment)  //fragmentlist를 인자값으로 받은 fragment를 추가
        //리스트에 새로운값이 추가되었다는것을 알려줌
        notifyItemInserted(fragmentlist.size-1)
    }
}