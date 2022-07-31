package dev.sergeitimoshenko.contacts.ui.contactdetails

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.sergeitimoshenko.contacts.R
import dev.sergeitimoshenko.contacts.databinding.FragmentContactDetailsBinding
import dev.sergeitimoshenko.contacts.ui.ContactsViewModel
import dev.sergeitimoshenko.contacts.ui.dialpad.DialpadFragment

@AndroidEntryPoint
class ContactDetailsFragment : Fragment(R.layout.fragment_contact_details) {

    companion object {
        private const val REQUEST_CALL = 1
    }

    private val args: ContactDetailsFragmentArgs by navArgs()
    private val model: ContactsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentContactDetailsBinding.bind(view)

        if (args.contact.email != null) binding.tvContactEmail.visibility = View.VISIBLE
        if (args.contact.image != null) binding.cvContactPhoto.visibility = View.VISIBLE

        binding.apply {
            tvContactName.text = args.contact.name
            tvContactSurname.text = args.contact.surname
            tvContactPhone.text = args.contact.phone
            tvContactEmail.text = args.contact.email
            ivContactPhoto.setImageBitmap(args.contact.image)

            btnEditContact.setOnClickListener {
                val action = ContactDetailsFragmentDirections.actionContactDetailsFragmentToUpdateContactFragment(args.contact)
                findNavController().navigate(action)
            }

            btnCall.setOnClickListener {
                makePhoneCall()
            }

            btnEmail.setOnClickListener {
                sendEmail()
            }

            btnSms.setOnClickListener {
                sendSms()
            }
        }

        model.contacts.observe(viewLifecycleOwner) {

        }
    }

    private fun makePhoneCall() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),
                ContactDetailsFragment.REQUEST_CALL
            )
        } else {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + args.contact.phone)))
        }
    }

    private fun sendEmail() {
        // TODO: Email Sending
        startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + args.contact.email)))
    }

    private fun sendSms() {
        // TODO: Sms Sending
        startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + args.contact.phone)))
    }
}