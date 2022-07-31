package dev.sergeitimoshenko.contacts.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sergeitimoshenko.contacts.R
import dev.sergeitimoshenko.contacts.databinding.ActivityContactsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {

    private val model: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle()

        // Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment!!.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    // TODO: Shitty Code
    private fun setTitle() {
        val title = "Contacts made with love"
        lifecycleScope.launch(Dispatchers.Main) {
            for (i in title.length downTo 0) {
                if (i != title.length && title[title.length - i - 1] == ' ') {
                    continue
                }
                runOnUiThread {
                    setTitle(title.substring(0, title.length - i))
                }
                delay(250)
            }
        }
    }
}