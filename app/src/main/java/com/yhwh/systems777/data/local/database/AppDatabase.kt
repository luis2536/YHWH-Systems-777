package com.yhwh.systems777.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yhwh.systems777.data.local.dao.LogDao
import com.yhwh.systems777.data.local.dao.NodeDao
import com.yhwh.systems777.data.local.entity.LogEntity
import com.yhwh.systems777.data.local.entity.NodeEntity

@Database(entities = [NodeEntity::class, LogEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nodeDao(): NodeDao
    abstract fun logDao(): LogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "yhwh_systems_database"
                )
                // Usamos fallback a destrucción migratoria para evitar bloat y crashes
                // en etapas tempranas de desarrollo
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
