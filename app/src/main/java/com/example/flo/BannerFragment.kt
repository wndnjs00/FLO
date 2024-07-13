package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBannerBinding
import com.example.flo.databinding.FragmentHomeBinding

class BannerFragment(val imgRes : Int) : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentBannerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBannerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImageResource()
    }


    private fun setImageResource(){
        //인자값으로 받은 이미지로 이미지뷰의 값변경
        binding.bannerImageIv.setImageResource(imgRes)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}