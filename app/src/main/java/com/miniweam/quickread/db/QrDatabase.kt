package com.miniweam.quickread.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miniweam.quickread.model.NewsData

@Database(entities = [NewsData::class], version = 1)
abstract class QrDatabase():RoomDatabase(){
    abstract fun qrDao():QrDao

    companion object {
        private var appDataBase: QrDatabase? = null

        fun getDatabase(context: Context): QrDatabase {
            if (appDataBase != null)
                return appDataBase!!

            appDataBase = Room.databaseBuilder(
                context.applicationContext,
                QrDatabase::class.java,
                "NEWS_DATABASE"
            )
                .build()
            return appDataBase!!
        }
    }

}
