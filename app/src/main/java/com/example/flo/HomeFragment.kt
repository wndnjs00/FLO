package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        //homeAlbumImgIv1를 눌렀을때 AlbumFragment로 이동
        binding.homeAlbumImgIv1.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
        }



        //viewpager를 만들기위해 fragment데이터를 추가하고, viewpager연결해줌

        //list안에 fragment추가
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp_1))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2_1))

        //viewpager와 adapter연결
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL    //좌우로 스크롤될수있도록



        //맨위에있는 viewpager만들기

        //list안에 fragment추가
        val bigbannerAdpter = BigBannerVPAdapter(this)
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_default_4_x_1))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp2))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp3))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp4))
        bigbannerAdpter.addImg(BigBannerFragment(R.drawable.img_album_exp5))

        //viewpager와 adapter연결
        binding.homePannelBackgroundVp.adapter = bigbannerAdpter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //viewpager와 indicator연결
        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)
        binding.homeBannerIndicator.setViewPager(binding.homeBannerVp)


        return binding.root




    }
}