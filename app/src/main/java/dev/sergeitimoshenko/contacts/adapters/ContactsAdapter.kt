package dev.sergeitimoshenko.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.sergeitimoshenko.contacts.databinding.ItemContactBinding
import dev.sergeitimoshenko.contacts.entities.Contact

class ContactsAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = getItem(position)
        holder.bind(currentContact)
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val contact = getItem(adapterPosition)
                    listener.onItemClick(contact, binding.root)
                }
            }
        }

        fun bind(contact: Contact) {

            if (contact.email != null) binding.tvEmail.visibility = View.VISIBLE
            if (contact.image != null) binding.cvPhoto.visibility = View.VISIBLE

            binding.apply {
                tvName.text = contact.name
                tvSurname.text = contact.surname
                tvEmail.text = contact.email
                tvPhone.text = contact.phone
                ivPhoto.setImageBitmap(contact.image)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(contact: Contact, view: View)
    }
}