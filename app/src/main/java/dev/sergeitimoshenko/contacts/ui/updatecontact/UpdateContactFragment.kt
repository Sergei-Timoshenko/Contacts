package dev.sergeitimoshenko.contacts.ui.updatecontact

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
import androidx.navigation.fragment.navArgs
import dev.sergeitimoshenko.contacts.R
import dev.sergeitimoshenko.contacts.databinding.FragmentUpdateContactBinding
import dev.sergeitimoshenko.contacts.entities.Contact
import dev.sergeitimoshenko.contacts.ui.ContactsViewModel

class UpdateContactFragment : Fragment(R.layout.fragment_update_contact) {

    val args: UpdateContactFragmentArgs by navArgs()
    val model: ContactsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUpdateContactBinding.bind(view)

        var image: Bitmap? = null
        val startForResult =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                binding.updateContact.ivSavePhoto.setImageURI(uri)
                image = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, uri!!)
                    ImageDecoder.decodeBitmap(source)
                }
            }

        binding.updateContact.apply {
            etSaveName.setText(args.contact.name)
            etSaveSurname.setText(args.contact.surname)
            etSavePhone.setText(args.contact.phone)
            etSaveEmail.setText(args.contact.email)
            cbIsImportant.isChecked = args.contact.isImportant
            ivSavePhoto.setImageBitmap(args.contact.image)

            cvSavePhoto.setOnClickListener {
                startForResult.launch("image/*")
            }

            btnSave.setOnClickListener {
                if (binding.updateContact.etSaveName.text.isNotEmpty() &&
                    binding.updateContact.etSaveSurname.text.isNotEmpty() &&
                    binding.updateContact.etSavePhone.text.isNotEmpty()
                ) {
                    val name = binding.updateContact.etSaveName.text.toString()
                    val surname = binding.updateContact.etSaveSurname.text.toString()
                    val phone = binding.updateContact.etSavePhone.text.toString()
                    val email = binding.updateContact.etSaveEmail.text.toString().ifEmpty { null }
                    val isImportant = binding.updateContact.cbIsImportant.isChecked

                    model.updateContact(Contact(name, surname, phone, email, image, isImportant, args.contact.id))
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