package com.example.a5_6lab.di

import android.app.Application
import androidx.room.Room
import com.example.a5_6lab.db.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideMainDb(App:Application): MainDb {
        return Room.databaseBuilder(
            App,
            MainDb::class.java,
            name = "info.db"
        ).createFromAsset("db/info.db").build()
    }
}