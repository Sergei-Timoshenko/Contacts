package dev.sergeitimoshenko.contacts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sergeitimoshenko.contacts.db.ContactDao
import dev.sergeitimoshenko.contacts.entities.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactDao: ContactDao
) : ViewModel() {

    val contacts = contactDao.getContacts()

    fun insertContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactDao.insertContact(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactDao.deleteContact(contact)
    }

    fun updateContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactDao.updateContact(contact)
    }
}