package dev.sergeitimoshenko.contacts.ui.contactslist

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.sergeitimoshenko.contacts.R
import dev.sergeitimoshenko.contacts.adapters.ContactsAdapter
import dev.sergeitimoshenko.contacts.databinding.FragmentContactsListBinding
import dev.sergeitimoshenko.contacts.entities.Contact
import dev.sergeitimoshenko.contacts.ui.ContactsViewModel

@AndroidEntryPoint
class ContactsListFragment : Fragment(R.layout.fragment_contacts_list), ContactsAdapter.OnItemClickListener {

    private val model: ContactsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentContactsListBinding.bind(view)

        // RecyclerView
        val contactsAdapter = ContactsAdapter(this)
        binding.rvContactsList.apply {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        // ItemTouchHelper
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val contact = model.contacts.value!![position]
                    model.deleteContact(contact)
                }
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.rvContactsList)

        model.contacts.observe(viewLifecycleOwner) { contacts ->
            contactsAdapter.submitList(contacts)
        }

        binding.fabAddContact.setOnClickListener {
            val action = ContactsListFragmentDirections.actionContactsFragmentToAddContactFragment()
            findNavController().navigate(action)
        }
    }

    override fun onItemClick(contact: Contact, view: View) {
        val action = ContactsListFragmentDirections.actionContactsFragmentToContactDetailsFragment(contact)
        findNavController().navigate(action)
    }
}