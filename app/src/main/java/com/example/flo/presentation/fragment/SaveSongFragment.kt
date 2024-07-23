package com.example.flo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.Song
import com.example.flo.SongDataBase
import com.example.flo.databinding.FragmentSavesongBinding
import com.example.flo.presentation.adapter.SavedSongRVAdapter

class SaveSongFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentSavesongBinding? = null

    lateinit var songDB: SongDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavesongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songDB = SongDataBase.getInstance(requireContext())!!
    }


    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedSongRVAdapter()

        songRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false,songId)
            }

        })
        binding.lockerSavedSongRecyclerView.adapter = songRVAdapter

        // 좋아요한 애들을 저장
        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}