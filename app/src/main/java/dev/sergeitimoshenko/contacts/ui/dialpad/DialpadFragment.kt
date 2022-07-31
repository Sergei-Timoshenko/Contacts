package dev.sergeitimoshenko.contacts.ui.dialpad

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dev.sergeitimoshenko.contacts.R
import dev.sergeitimoshenko.contacts.databinding.FragmentDialpadBinding

class DialpadFragment : Fragment(R.layout.fragment_dialpad) {

    companion object {
        private const val REQUEST_CALL = 1
    }

    private val model: DialpadViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDialpadBinding.bind(view)

        model.enteredPhoneNumber.observe(viewLifecycleOwner) { phoneNumber ->
            binding.tvEnteredNumber.text = phoneNumber
        }

        binding.cvNumberOne.setOnClickListener {
            model.enterDigit("1")
        }

        binding.cvNumberTwo.setOnClickListener {
            model.enterDigit("2")
        }

        binding.cvNumberThree.setOnClickListener {
            model.enterDigit("3")
        }

        binding.cvNumberFour.setOnClickListener {
            model.enterDigit("4")
        }

        binding.cvNumberFive.setOnClickListener {
            model.enterDigit("5")
        }

        binding.cvNumberSix.setOnClickListener {
            model.enterDigit("6")
        }

        binding.cvNumberSeven.setOnClickListener {
            model.enterDigit("7")
        }

        binding.cvNumberEight.setOnClickListener {
            model.enterDigit("8")
        }

        binding.cvNumberNine.setOnClickListener {
            model.enterDigit("9")
        }

        binding.cvNumberZero.setOnClickListener {
            model.enterDigit("0")
        }

        binding.cvRemove.setOnClickListener {
            model.removeDigit()
        }

        binding.cvCall.setOnClickListener {
            if (model.enteredPhoneNumber.value?.length != null) {
                makePhoneCall()
                model.reset()
            } else {
                Toast.makeText(requireContext(), "Enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makePhoneCall() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL)
        } else {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + model.enteredPhoneNumber.value)))
        }
    }
}