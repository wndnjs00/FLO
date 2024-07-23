package com.example.flo.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.LoginActivity
import com.example.flo.MainActivity
import com.example.flo.presentation.adapter.LockerVPAdapter
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentLockerBinding? = null

    //fragment_locker Tablayout에 들어갈 텍스트
    private val information = arrayListOf("저장한 곡", "저장앨범")

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
        setOnClickListner()
        initView()
    }


    private fun setViewPager(){
        //view와 연결해주는 작업
        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter

        //TabLayout과 ViewPager연결
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
            //TabLayout에 들어갈 텍스트적음
                tab, position ->
            tab.text = information[position]
        }.attach()
    }

    private fun setOnClickListner(){
        binding.lockerLoginTv.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // sharedpreference에 jwt값이 저장되어있는지 확인후, 로그인 로그아웃 버튼 변경하기
    private fun getJwt(): Int {
        val sharedpreference = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return sharedpreference?.getInt("jwt", 0) ?: 0
    }

    private fun initView(){
        val jwt : Int = getJwt()
        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity,LoginActivity::class.java))
            }
        }else{
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                logout()
                startActivity(Intent(activity,MainActivity::class.java))
            }
        }
    }

    private fun logout(){
        val sharedpreference = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedpreference?.edit()
        editor?.remove("jwt")
        editor?.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}