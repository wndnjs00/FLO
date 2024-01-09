package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBigbannerBinding

class BigBannerFragment(val imgRes : Int) : Fragment() {

    lateinit var binding : FragmentBigbannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding 초기화
        binding = FragmentBigbannerBinding.inflate(inflater, container, false)


        //인자값으로 받은 이미지로 이미지뷰의 값변경
        binding.homePannelImageBakgroundIv.setImageResource(imgRes)
        return binding.root
    }
}