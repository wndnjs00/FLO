package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    //fragment_locker Tablayout에 들어갈 텍스트
    private val information = arrayListOf("저장한 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)


        //view와 연결해주는 작업
        val lockerAdapter = LockerVPAdapter(this)
        binding.lockContentVp.adapter = lockerAdapter

        //TabLayout과 ViewPager연결
        TabLayoutMediator(binding.lockContentTb, binding.lockContentVp){
            //TabLayout에 들어갈 텍스트적음
            tab, position ->
            tab.text = information[position]
        }.attach()


        return binding.root
    }
}