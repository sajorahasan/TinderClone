package com.sajorahasan.tinderclone.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sajorahasan.tinderclone.model.User
import com.sajorahasan.tinderclone.room.converter.LocationConverter
import com.sajorahasan.tinderclone.room.converter.NameConverter
import com.sajorahasan.tinderclone.room.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(value = [LocationConverter::class, NameConverter::class])
abstract class TinderRoomDb : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: TinderRoomDb? = null

        fun getDatabase(context: Context): TinderRoomDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TinderRoomDb::class.java,
                    "TinderDb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}