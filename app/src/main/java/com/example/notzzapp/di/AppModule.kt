package com.example.notzzapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.notzzapp.datastore.UIModeDataStore
import com.example.notzzapp.datastore.UIModeImpl
import com.example.notzzapp.db.NotesDao
import com.example.notzzapp.db.NotesDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): UIModeImpl {
        return UIModeDataStore(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: NotesDatabase): NotesDao = database.getNotesDao()

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NotesDatabase =
        Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes_db.db"
        ).fallbackToDestructiveMigration().build()
}
