package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment(){

    lateinit var binding : FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)

        return binding.root


//        //취향MIX 버튼 눌렀을때 이미지 바뀌게g
//        binding.songMixoffTg.setOnClickListener {
//            Mix(false)
//        }
//
//        binding.songMixoffTg.setOnClickListener {
//            Mix(true)
//        }



    }



//    //취향MIX 버튼 눌렀을때 바뀌는 함수
//    fun Mix(go: Boolean){
//        if (go){
//            binding.songMixoffTg.visibility = View.VISIBLE
//            binding.songMixonTg.visibility = View.GONE
//        }
//        else{
//            binding.songMixoffTg.visibility = View.GONE
//            binding.songMixonTg.visibility = View.VISIBLE
//        }
//    }


}