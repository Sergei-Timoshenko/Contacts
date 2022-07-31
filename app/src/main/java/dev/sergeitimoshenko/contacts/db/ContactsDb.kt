package dev.sergeitimoshenko.contacts.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sergeitimoshenko.contacts.entities.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
@TypeConverters(ImageConverter::class)
abstract class ContactsDb : RoomDatabase() {

    abstract fun getContactDao(): ContactDao
}