package com.example.flo

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding


    //fragment_album TabLayout에 들어갈 텍스트
    private val information = arrayListOf("수록곡", "상세정보","영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)


        //albumBackIv버튼 클릭했을때
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
        }


        //좋아요 눌렀을때
        binding.albumLikeIv.setOnClickListener {
            favorateBtn(false)
        }

        //좋아요 해제했을때
        binding.albumLikeClickIv.setOnClickListener {
            favorateBtn(true)
        }




        //view와 연결해주는 작업
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter


        //TabLayout과 ViewPager연결
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            //TabLayout에 들어갈 텍스트적음
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }



    //좋아요 눌렀을때 바뀌는 함수
   fun favorateBtn(favorate : Boolean) {
       if (favorate){
           binding.albumLikeIv.visibility = View.VISIBLE
           binding.albumLikeClickIv.visibility = View.GONE
       }
        else{
            binding.albumLikeIv.visibility = View.GONE
           binding.albumLikeClickIv.visibility = View.VISIBLE
       }
   }


}