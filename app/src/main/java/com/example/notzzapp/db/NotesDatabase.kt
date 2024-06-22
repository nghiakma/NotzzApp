package com.example.notzzapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notzzapp.model.Notes

@Database(
    entities = [Notes::class],
    version = 1,
    exportSchema = true
)

abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}
