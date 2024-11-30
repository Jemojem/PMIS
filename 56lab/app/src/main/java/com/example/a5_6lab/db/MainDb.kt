package com.example.a5_6lab.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.a5_6lab.utils.ListItem

@Database(
    entities = [ListItem::class],
    version = 1
)
abstract class MainDb: RoomDatabase() {
    abstract val dao:Dao;
}