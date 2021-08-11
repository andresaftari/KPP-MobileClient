package com.kppmining.client

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.databinding.ActivityMainBinding

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

        val navView = binding.navView
        val navController = findNavController(R.id.navHostFragment)

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

//        binding.navView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_home -> {
//                    binding.toolbar1.tvUser.visibility = View.VISIBLE
//                    binding.toolbar1.civAvatar.visibility = View.VISIBLE
//
//                    supportActionBar!!.setDisplayShowTitleEnabled(false)
//                    setupBottomNav(HomeFragment())
//
//                    true
//                }
//                R.id.navigation_profile -> {
//                    binding.toolbar1.tvUser.visibility = View.GONE
//                    binding.toolbar1.civAvatar.visibility = View.GONE
//
//                    supportActionBar!!.apply {
//                        setDisplayShowTitleEnabled(true)
//                        title = "Profil"
//                    }
//                    setupBottomNav(ProfileFragment())
//
//                    true
//                }
//                R.id.navigation_history -> {
//                    binding.toolbar1.tvUser.visibility = View.GONE
//                    binding.toolbar1.civAvatar.visibility = View.GONE
//
//                    supportActionBar!!.apply {
//                        setDisplayShowTitleEnabled(true)
//                        title = "Riwayat"
//                    }
//                    setupBottomNav(HistoryFragment())
//
//                    true
//                }
//                R.id.navigation_setting -> {
//                    binding.toolbar1.tvUser.visibility = View.GONE
//                    binding.toolbar1.civAvatar.visibility = View.GONE
//
//                    supportActionBar!!.apply {
//                        setDisplayShowTitleEnabled(true)
//                        title = "Pengaturan"
//                    }
//                    setupBottomNav(SettingFragment())
//
//                    true
//                }
//                else -> false
//            }
//        }
    }
}