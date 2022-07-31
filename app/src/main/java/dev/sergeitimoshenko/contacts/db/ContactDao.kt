package dev.sergeitimoshenko.contacts.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.sergeitimoshenko.contacts.entities.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts_table ORDER BY contact_name ASC")
    fun getContacts(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)
}