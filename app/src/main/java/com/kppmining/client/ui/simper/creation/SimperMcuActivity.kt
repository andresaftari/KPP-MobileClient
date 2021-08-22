package com.kppmining.client.ui.simper.creation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kppmining.client.MainActivity
import com.kppmining.client.R
import com.kppmining.client.databinding.ActivitySimperMcuBinding
import com.kppmining.core.domain.model.DummyAccount

class SimperMcuActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimperMcuBinding
    private var account = DummyAccount()
    private var isSuccess = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimperMcuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()
        setMCUStatus(isSuccess)
    }

    private fun setMCUStatus(isSuccess: Boolean) = when (isSuccess) {
        true -> with(binding) {
            tvStatusHint.visibility = View.VISIBLE

            Glide.with(root)
                .load(R.drawable.step_1)
                .into(step1)

            tvStatus.text = resources.getString(R.string.text_status_simper_mcu_ok)
            tvStatusHint.text = resources.getString(R.string.text_status_hint)
        }
        false -> with(binding) {
            tvStatusHint.visibility = View.GONE

            Glide.with(root)
                .load(R.drawable.step_1_unclear)
                .into(step1)

            tvStatus.text = resources.getString(R.string.text_status_simper_mcu)
        }
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