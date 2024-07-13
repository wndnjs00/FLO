package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment(){

    private val binding get() = _binding!!
    private var _binding: FragmentSongBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
    }


    private fun clickListener() {
        with(binding){
            //songMixoffTg버튼 클릭했을때 이미지변경
            songMixoffTg.setOnClickListener {
                songMixoffTg.visibility = View.GONE
                songMixonTg.visibility = View.VISIBLE
            }

            songMixonTg.setOnClickListener{
                songMixoffTg.visibility = View.VISIBLE
                songMixonTg.visibility = View.GONE
            }
        }
    }


//        //취향MIX 버튼 눌렀을때 이미지 바뀌게g
//        binding.songMixoffTg.setOnClickListener {
//            Mix(false)
//        }
//
//        binding.songMixoffTg.setOnClickListener {
//            Mix(true)
//        }



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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}