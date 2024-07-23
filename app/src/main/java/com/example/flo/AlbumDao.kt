package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums() : List<Album>

    // 사용자가 좋아요한 앨범을 추가하는 함수
    @Insert
    fun likeAlbum(like : Like)

    // userId와 albumId를 비교해서 같은 id가 있으면 그걸 LikeTable에 반환해달라는 함수
    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun isLikedAlbum(userId : Int, albumId : Int) : Int?

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId : Int, albumId : Int)

    // LikeTable과 AlbumTable을 join해서, LikeTable의 userId가 userId와 일치하는행만 가져오는 함수
    // 사용자가 좋아하는 앨범을 조회하기 위함
    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId")
    fun getLikedAlbum(userId : Int) : List<Album>
}