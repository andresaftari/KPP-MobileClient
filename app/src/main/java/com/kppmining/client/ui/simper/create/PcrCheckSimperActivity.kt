package com.kppmining.client.ui.simper.create

import android.app.DatePickerDialog
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
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.ActivityPcrCheckSimperBinding
import com.kppmining.client.databinding.ComponentDialogSubmitBinding
import com.kppmining.client.utils.FORM_IMAGE_PICKER_CHECK
import com.kppmining.core.domain.model.DummyAccount
import com.kppmining.core.utils.ImageCompressor
import java.io.File
import java.io.IOException
import java.io.InvalidObjectException
import java.text.SimpleDateFormat
import java.util.*

class PcrCheckSimperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPcrCheckSimperBinding

    private var account = DummyAccount()
    private var statusPCR = ""

    private var getPcrPhoto: File? = null
    private var isPcrClicked = false

    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPcrCheckSimperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()

        with(binding) {
            edtDate.setOnClickListener { inputDate() }
            edtScanPcr.setOnClickListener {
                isPcrClicked = true
                getPhoto()
            }

            btnPositif.setOnClickListener {
                statusPCR = "Positif"

                btnNegatif.setBackgroundColor(Color.parseColor("#EFEDEE"))
                btnNegatif.setTextColor(Color.parseColor("#B0B0B0"))

                btnPositif.setBackgroundColor(Color.parseColor("#FA4949"))
                btnPositif.setTextColor(Color.WHITE)
            }
            btnNegatif.setOnClickListener {
                statusPCR = "Negatif"

                btnPositif.setBackgroundColor(Color.parseColor("#EFEDEE"))
                btnPositif.setTextColor(Color.parseColor("#B0B0B0"))

                btnNegatif.setBackgroundColor(Color.parseColor("#B8DE9C"))
                btnNegatif.setTextColor(Color.WHITE)
            }

            btnSubmit.setOnClickListener { sendData() }
        }
    }

    private fun getPhoto() = ImagePicker.create(this).single().start()

    private fun inputDate() {
        val dateSet = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            with(calendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            val dateFormat = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(dateFormat, Locale("ID"))

            binding.edtDate.setText(sdf.format(calendar.time))
        }

        DatePickerDialog(
            this, dateSet,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun returnUsername() {
        if (intent!!.hasExtra("username")) {
            val data = intent!!.getParcelableExtra<DummyAccount>("username")
            this.account = data!!

            Log.d("CHECK_USERNAME", data.username)
        }
    }

    private fun sendData() = with(binding) {
        val scanPCRData = edtScanPcr.text.toString()
        val pcrDateData = edtDate.text.toString()

        when {
            TextUtils.equals(pcrDateData, "DD-MM-YYYY") -> edtDate.apply {
                error = "Silakan isi tanggal tes PCR"
                requestFocus()
            }
            TextUtils.equals(scanPCRData, "Pilih Gambar") -> edtScanPcr.apply {
                error = "Silakan isi Scan hasil PCR"
                requestFocus()
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
                        this, McuValidationSimperActivity::class.java
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

                if (isPcrClicked) {
                    getPcrPhoto = filePhoto
                    binding.edtScanPcr.setText(filePhoto.name.toString())
                }
            } catch (ex: InvalidObjectException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.pcrCheck", "${ex.printStackTrace()}")
            } catch (ex: IOException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.pcrCheck", "${ex.printStackTrace()}")
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