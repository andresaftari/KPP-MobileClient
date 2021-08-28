package com.kppmining.client.ui.simper.create

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.ActivityDdtInputResultSimperBinding
import com.kppmining.client.databinding.ComponentDialogSubmitBinding
import com.kppmining.core.domain.model.DummyAccount

class DdtInputResultSimperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDdtInputResultSimperBinding

    private var account = DummyAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDdtInputResultSimperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()

        with(binding) {
            btnSubmit.setOnClickListener { sendData() }
            btnSave.setOnClickListener {
                Snackbar.make(
                    binding.root,
                    "Fitur download masih dalam pengembangan",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun sendData() = with(binding) {
        val nrpData = edtNrp.text.toString()
        val namedata = edtName.text.toString()
        val scoreData = edtScore.text.toString()
        val deptData = edtDept.text.toString()

        when {
            TextUtils.isEmpty(nrpData) -> {
                edtNrp.apply {
                    error = "Silakan isi NRP"
                    requestFocus()
                }
            }
            TextUtils.isEmpty(namedata) -> {
                edtName.apply {
                    error = "Silakan isi nama lengkap Anda"
                    requestFocus()
                }
            }
            TextUtils.isEmpty(scoreData) -> {
                edtScore.apply {
                    error = "Silakan isi nilai DDT Anda"
                    requestFocus()
                }
            }
            TextUtils.isEmpty(deptData) -> {
                edtDept.apply {
                    error = "Silakan isi nama departemen"
                    requestFocus()
                }
            }
            else -> startDialog()
        }
    }

    private fun startDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val dialogBinding = ComponentDialogSubmitBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
                startActivity(
                    Intent(
                        this, GroundRegistrationSimperActivity::class.java
                    ).putExtra("username", account)
                )
                finish()
            }.show()
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