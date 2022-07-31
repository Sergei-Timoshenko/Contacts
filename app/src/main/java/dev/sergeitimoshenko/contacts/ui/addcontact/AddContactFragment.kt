package dev.sergeitimoshenko.contacts.ui.addcontact

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dev.sergeitimoshenko.contacts.R
import dev.sergeitimoshenko.contacts.databinding.FragmentAddContactBinding
import dev.sergeitimoshenko.contacts.entities.Contact
import dev.sergeitimoshenko.contacts.ui.ContactsViewModel

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    private val model: ContactsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddContactBinding.bind(view)

        var image: Bitmap? = null
        val startForResult =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                binding.addContact.ivSavePhoto.setImageURI(uri)
                image = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, uri!!)
                    ImageDecoder.decodeBitmap(source)
                }
            }

        binding.addContact.apply {
            cvSavePhoto.setOnClickListener {
                startForResult.launch("image/*")
            }

            btnSave.setOnClickListener {
                if (binding.addContact.etSaveName.text.isNotEmpty() &&
                    binding.addContact.etSaveSurname.text.isNotEmpty() &&
                    binding.addContact.etSavePhone.text.isNotEmpty()
                ) {
                    val name = binding.addContact.etSaveName.text.toString()
                    val surname = binding.addContact.etSaveSurname.text.toString()
                    val phone = binding.addContact.etSavePhone.text.toString()
                    val email = binding.addContact.etSaveEmail.text.toString().ifEmpty { null }
                    val isImportant = binding.addContact.cbIsImportant.isChecked

                    model.insertContact(Contact(name, surname, phone, email, image, isImportant))
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Enter a name, a surname and a phone number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}