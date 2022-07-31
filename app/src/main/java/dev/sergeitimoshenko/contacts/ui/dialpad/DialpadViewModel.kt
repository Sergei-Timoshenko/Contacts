package dev.sergeitimoshenko.contacts.ui.dialpad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialpadViewModel : ViewModel() {

    private val _enteredPhoneNumber = MutableLiveData<String>()
    val enteredPhoneNumber: LiveData<String> = _enteredPhoneNumber

    fun enterDigit(digit: String) {
        _enteredPhoneNumber.value = (_enteredPhoneNumber.value ?: "") + digit
    }

    fun removeDigit() {
        _enteredPhoneNumber.value = _enteredPhoneNumber.value?.dropLast(1)
    }

    fun reset() {
        _enteredPhoneNumber.value = ""
    }
}