package dev.sergeitimoshenko.contacts.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sergeitimoshenko.contacts.db.ContactsDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactsModule {

    @Provides
    @Singleton
    fun provideTaskDb(
        app: Application
    ) = Room.databaseBuilder(
        app,
        ContactsDb::class.java,
        "contacts_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideContactDao(db: ContactsDb) = db.getContactDao()
}