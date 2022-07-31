package dev.sergeitimoshenko.contacts.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "contacts_table")
@Parcelize
data class Contact(
    @ColumnInfo(name = "contact_name")
    val name: String,
    @ColumnInfo(name = "contact_surname")
    val surname: String,
    @ColumnInfo(name = "contact_phone")
    val phone: String,
    @ColumnInfo(name = "contact_email")
    val email: String? = null,
    @ColumnInfo(name = "contact_image")
    val image: Bitmap? = null,
    @ColumnInfo(name = "contact_is_important")
    val isImportant: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val id: Int? = null,
    // Wtf is this?
    val position: Int? = null
) : Parcelable
