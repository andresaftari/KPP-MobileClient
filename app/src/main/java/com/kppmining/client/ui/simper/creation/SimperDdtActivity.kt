package com.kppmining.client.ui.simper.creation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.ActivitySimperDdtBinding
import com.kppmining.core.domain.model.DummyAccount

class SimperDdtActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimperDdtBinding
    private var account = DummyAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimperDdtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()
    }

    private fun returnUsername() {
        if (intent!!.hasExtra("username")) {
            val data = intent!!.getParcelableExtra<DummyAccount>("username")
            this.account = data!!

            Log.d("CHECK_USERNAME", data.username)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).putExtra("username", account)
                )
                finish()
                return true
            }
        }
        return false
    }
}