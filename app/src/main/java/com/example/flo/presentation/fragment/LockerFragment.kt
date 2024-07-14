package com.example.flo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.presentation.adapter.LockerVPAdapter
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentLockerBinding? = null

    //fragment_locker Tablayout에 들어갈 텍스트
    private val information = arrayListOf("저장한 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()
    }


    private fun setViewPager(){
        //view와 연결해주는 작업
        val lockerAdapter = LockerVPAdapter(this)
        binding.lockContentVp.adapter = lockerAdapter

        //TabLayout과 ViewPager연결
        TabLayoutMediator(binding.lockContentTb, binding.lockContentVp){
            //TabLayout에 들어갈 텍스트적음
                tab, position ->
            tab.text = information[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}