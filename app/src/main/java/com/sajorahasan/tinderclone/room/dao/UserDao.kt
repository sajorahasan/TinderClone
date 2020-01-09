package com.sajorahasan.tinderclone.room.dao

import androidx.room.*
import com.sajorahasan.tinderclone.model.User

@Dao
interface UserDao {

    @Query("SELECT * from User")
    fun getFavUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFav(user: User)
}