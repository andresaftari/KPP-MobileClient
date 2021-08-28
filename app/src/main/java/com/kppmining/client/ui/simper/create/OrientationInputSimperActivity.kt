package com.kppmining.client.ui.simper.create

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.esafirm.imagepicker.features.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.ActivityOrientationInputSimperBinding
import com.kppmining.client.databinding.ComponentDialogSubmitBinding
import com.kppmining.client.utils.FORM_IMAGE_PICKER_CHECK
import com.kppmining.core.domain.model.DummyAccount
import com.kppmining.core.utils.ImageCompressor
import java.io.File
import java.io.IOException
import java.io.InvalidObjectException

class OrientationInputSimperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrientationInputSimperBinding

    private var account = DummyAccount()
    private var employeeStatus = ""

    private var getHaulingPhoto: File? = null
    private var getSimperPhoto: File? = null
    private var getGroundTestPhoto: File? = null

    private var isHaulingClicked = false
    private var isSimperClicked = false
    private var isGroundTestClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrientationInputSimperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()

        with (binding) {
            btnExisting.setOnClickListener {
                employeeStatus = "Karyawan Lama"

                btnNew.setBackgroundColor(Color.parseColor("#EFEDEE"))
                btnNew.setTextColor(Color.parseColor("#B0B0B0"))

                btnExisting.setBackgroundColor(Color.parseColor("#B8DE9C"))
                btnExisting.setTextColor(Color.WHITE)
            }
            btnNew.setOnClickListener {
                employeeStatus = "Karyawan Baru"

                btnExisting.setBackgroundColor(Color.parseColor("#EFEDEE"))
                btnExisting.setTextColor(Color.parseColor("#B0B0B0"))

                btnNew.setBackgroundColor(Color.parseColor("#B8DE9C"))
                btnNew.setTextColor(Color.WHITE)
            }

            edtHauling.setOnClickListener {
                isHaulingClicked = true
                getPhoto()
            }
            edtFcSimper.setOnClickListener {
                isSimperClicked = true
                getPhoto()
            }
            edtGtResult.setOnClickListener {
                isGroundTestClicked = true
                getPhoto()
            }

            btnSubmit.setOnClickListener { sendData() }
        }
    }

    private fun getPhoto() = ImagePicker.create(this).single().start()

    private fun sendData() = with(binding) {
        val haulingData = edtHauling.text.toString()
        val simperData = edtFcSimper.text.toString()
        val groundTest = edtGtResult.text.toString()

        when {
            (employeeStatus == "") -> {
                Snackbar.make(
                    binding.root,
                    "Anda belum memilih jenis karyawan!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            TextUtils.equals(haulingData, "Pilih Gambar") -> {
                edtHauling.apply {
                    error = "Silakan isi Ketentuan Jalan Hauling"
                    requestFocus()
                }
            }
            TextUtils.equals(simperData, "Pilih Gambar") -> {
                edtFcSimper.apply {
                    error = "Silakan isi Fotocopy SIMPER"
                    requestFocus()
                }
            }
            TextUtils.equals(groundTest, "Pilih Gambar") -> {
                edtGtResult.apply {
                    error = "Silakan isi Hasil Ground Test"
                    requestFocus()
                }
            }
            else -> startDialog()
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
                startActivity(
                    Intent(
                        this, OrientationValidationSimperActivity::class.java
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

                if (isHaulingClicked) {
                    getHaulingPhoto = filePhoto
                    binding.edtHauling.setText(filePhoto.name.toString())
                }
                if (isSimperClicked) {
                    getSimperPhoto = filePhoto
                    binding.edtFcSimper.setText(filePhoto.name.toString())
                }
                if (isGroundTestClicked) {
                    getGroundTestPhoto = filePhoto
                    binding.edtGtResult.setText(filePhoto.name.toString())
                }

            } catch (ex: InvalidObjectException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.Simperorientation", "${ex.printStackTrace()}")
            } catch (ex: IOException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.Simperorientation", "${ex.printStackTrace()}")
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