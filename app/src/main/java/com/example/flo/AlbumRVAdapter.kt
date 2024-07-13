package com.example.flo

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(
    private val albumList : ArrayList<Album>,
    private val itemClickListener : (Album) -> Unit
) : RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])

        holder.itemView.setOnClickListener {
            itemClickListener(albumList[position])
        }
    }

    override fun getItemCount(): Int = albumList.size


    inner class ViewHolder(val binding : ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}