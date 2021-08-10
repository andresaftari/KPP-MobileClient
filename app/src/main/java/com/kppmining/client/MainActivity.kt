package com.kppmining.client

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.databinding.ActivityMainBinding
import com.kppmining.core.domain.model.DummyAccount

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent!!.hasExtra("message")) {
            val message = intent!!.getStringExtra("message")
            Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupBottomNav()

        if (intent!!.hasExtra("username")) {
            val accounts = intent!!.getParcelableExtra<DummyAccount>("username")
            binding.toolbar1.tvUser.text = "Hi, ${accounts!!.username}"
        }

//        binding.navView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_home -> {
//                    setupBottomNav()
//
//                    binding.toolbar1.tvUser.visibility = View.VISIBLE
//                    binding.toolbar1.civAvatar.visibility = View.VISIBLE
//
//                    supportActionBar!!.setDisplayShowTitleEnabled(false)
//
//                    true
//                }
//                R.id.navigation_profile -> {
//                    setupBottomNav()
//
//                    binding.toolbar1.tvUser.visibility = View.GONE
//                    binding.toolbar1.civAvatar.visibility = View.GONE
//
//                    supportActionBar!!.apply {
//                        setDisplayShowTitleEnabled(true)
//                        title = "Profil"
//                    }
//
//                    true
//                }
//                R.id.navigation_history -> {
//                    setupBottomNav()
//
//                    binding.toolbar1.tvUser.visibility = View.GONE
//                    binding.toolbar1.civAvatar.visibility = View.GONE
//
//                    supportActionBar!!.apply {
//                        setDisplayShowTitleEnabled(true)
//                        title = "Riwayat"
//                    }
//
//                    true
//                }
//                R.id.navigation_setting -> {
//                    setupBottomNav()
//
//                    binding.toolbar1.tvUser.visibility = View.GONE
//                    binding.toolbar1.civAvatar.visibility = View.GONE
//
//                    supportActionBar!!.apply {
//                        setDisplayShowTitleEnabled(true)
//                        title = "Pengaturan"
//                    }
//
//                    true
//                }
//                else -> false
//            }
//        }
    }

    private fun setupBottomNav() {
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_profile,
                R.id.navigation_history,
                R.id.navigation_setting
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}