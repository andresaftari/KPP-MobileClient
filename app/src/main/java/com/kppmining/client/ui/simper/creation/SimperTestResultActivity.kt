package com.kppmining.client.ui.simper.creation

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
import com.kppmining.client.databinding.ActivitySimperTestResultBinding
import com.kppmining.client.databinding.ComponentDialogSubmitBinding
import com.kppmining.client.utils.FORM_IMAGE_PICKER_CHECK
import com.kppmining.core.domain.model.DummyAccount
import com.kppmining.core.utils.ImageCompressor
import java.io.File
import java.io.IOException
import java.io.InvalidObjectException

class SimperTestResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimperTestResultBinding
    private var account = DummyAccount()

    private var groundTestImage: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimperTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()

        with(binding) {
            edtScanGroundtest.setOnClickListener { getPhoto() }
            btnSubmit.setOnClickListener { sendData() }
        }
    }

    private fun sendData() = with(binding) {
        val nikData = edtNik.text.toString()
        val nameData = edtNama.text.toString()
        val scanGTData = edtScanGroundtest.text.toString()

        when {
            TextUtils.isEmpty(nikData) -> {
                edtNik.apply {
                    error = "Silakan isi NIK Anda"
                    requestFocus()
                }
            }
            TextUtils.isEmpty(nameData) -> {
                edtNama.apply {
                    error = "Silakan isi NIK Anda"
                    requestFocus()
                }
            }
            TextUtils.equals(scanGTData, "Pilih Gambar") -> {
                edtScanGroundtest.apply {
                    error = "Silakan isi Salinan Hasil Ground Test"
                    requestFocus()
                }
            }
            else -> startDialog()
        }
    }

    private fun getPhoto() = ImagePicker.create(this).single().start()

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

                groundTestImage = filePhoto

                binding.edtScanGroundtest.setText(filePhoto.name)
            } catch (ex: InvalidObjectException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.testResultSimper", "${ex.printStackTrace()}")
            } catch (ex: IOException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.testResultSimper", "${ex.printStackTrace()}")
            }
        }
    }

    private fun returnUsername() {
        if (intent!!.hasExtra("username")) {
            val data = intent!!.getParcelableExtra<DummyAccount>("username")
            this.account = data!!

            Log.d("CHECK_USERNAME", data.username)
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
//                startActivity(
//                    Intent(
//                        this, SimperSelectionActivity::class.java
//                    ).putExtra("username", account)
//                )
                finish()
            }
            .show()
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