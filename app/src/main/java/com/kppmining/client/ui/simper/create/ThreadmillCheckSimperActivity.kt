package com.kppmining.client.ui.simper.create

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.esafirm.imagepicker.features.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.ActivityThreadmillCheckSimperBinding
import com.kppmining.client.databinding.ComponentDialogSubmitBinding
import com.kppmining.client.utils.FORM_IMAGE_PICKER_CHECK
import com.kppmining.core.domain.model.DummyAccount
import com.kppmining.core.utils.ImageCompressor
import java.io.File
import java.io.IOException
import java.io.InvalidObjectException

class ThreadmillCheckSimperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThreadmillCheckSimperBinding

    private var account = DummyAccount()

    private var getMcuPhoto: File? = null
    private var isMcuClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreadmillCheckSimperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()

        with(binding) {
            edtThreadmill.setOnClickListener {
                isMcuClicked = true
                getPhoto()
            }
            btnSubmit.setOnClickListener { sendData() }
        }
    }

    private fun getPhoto() = ImagePicker.create(this).single().start()

    private fun returnUsername() {
        if (intent!!.hasExtra("username")) {
            val data = intent!!.getParcelableExtra<DummyAccount>("username")
            this.account = data!!

            Log.d("CHECK_USERNAME", data.username)
        }
    }

    private fun sendData() = with(binding) {
        val scanMCUData = edtThreadmill.text.toString()

        when {
            TextUtils.equals(scanMCUData, "Pilih Gambar") -> {
                edtThreadmill.apply {
                    error = "Silakan isi hasil Scan MCU"
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
                        this, PcrCheckSimperActivity::class.java
                    ).putExtra("username", account)
                )
                finish()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val pickedImage = ImagePicker.getFirstImageOrNull(data)

            try {
                val parcelFileDecriptor = this
                    .contentResolver.openFileDescriptor(pickedImage.uri, "r")!!

                val fileDecriptor = parcelFileDecriptor.fileDescriptor
                val image = BitmapFactory.decodeFileDescriptor(fileDecriptor)
                val filePhoto = ImageCompressor.addTempFile(this, image)

                if (isMcuClicked) {
                    getMcuPhoto = filePhoto
                    binding.edtThreadmill.setText(filePhoto.name.toString())
                }

            } catch (ex: InvalidObjectException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.threadmillCheck", "${ex.printStackTrace()}")
            } catch (ex: IOException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.threadmillCheck", "${ex.printStackTrace()}")
            }
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