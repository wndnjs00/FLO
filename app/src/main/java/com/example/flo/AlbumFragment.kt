package com.example.flo

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding

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