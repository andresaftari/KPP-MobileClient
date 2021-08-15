package com.kppmining.client.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.ActivityLoginBinding
import com.kppmining.core.domain.model.DummyAccount

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("logout_message")) {
            val message = intent.getStringExtra("logout_message")
            Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        binding.btnLogin.setOnClickListener { setData() }
    }

    private fun setData() {
        val usernameData = binding.edtLogin.text.toString()
        val passwordData = binding.edtPass.text.toString()

        checkInputData(usernameData, passwordData)
    }

    private fun checkInputData(username: String, password: String) {
        with(binding) {
            when {
                TextUtils.isEmpty(username) -> {
                    edtLogin.apply {
                        error = "Silakan isi nama pengguna"
                        requestFocus()
                    }
                }
                TextUtils.isEmpty(password) -> {
                    edtPass.apply {
                        error = "Silakan isi kata sandi"
                        requestFocus()
                    }
                }
                else -> {
                    val savedAccount = DummyAccount(username)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java).apply {
                        putExtra("username", savedAccount)
                        putExtra("message", "Anda berhasil login ${savedAccount.username}")
                    })
                    finish()
                }
            }
        }
    }
}